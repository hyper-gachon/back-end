package com.gachon.hypergachon.crawling.service;

import com.gachon.hypergachon.crawling.entity.CyberCampusEntity;
import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CyberCampusService {

    // 사용자 이름과 비밀번호 (추후 앱에서 입력받은 데이터로 수정)
    private static final String USERID = "ha70801324";
    private static final String PASSWORD = "fldkfldk#01";

    public String getCyberCampus() {
        // WebDriver 초기화
        WebDriverManager.chromedriver().setup();

        // WebDriver 객체 생성
        WebDriver driver = new ChromeDriver();

        try {
            // 로그인 페이지로 이동
            driver.get("https://sso.gachon.ac.kr/svc/tk/Auth.do?ac=Y&ifa=N&id=cyber&RelayState=/exsignon/sso/sso.php");
            Thread.sleep(1000); // 1초대기

            // 사용자 이름과 비밀번호 입력
            WebElement usernameInput = driver.findElement(By.cssSelector(".ico_01"));
            WebElement passwordInput = driver.findElement(By.cssSelector(".ico_02"));
            usernameInput.sendKeys(USERID);
            passwordInput.sendKeys(PASSWORD);

            // 로그인 버튼 클릭
            WebElement loginButton = driver.findElement(By.className("btn_login"));
            loginButton.click();

            // 로그인 이후 이동할 페이지로 이동
            driver.get("https://cyber.gachon.ac.kr/local/ubion/user/?year=2023&semester=10");
            Thread.sleep(1000); // 1초대기

            // 이동한 페이지의 HTML 코드 크롤링
            String html = driver.getPageSource();
            List<Map<String, String>> courseInfo = getCourseId(html);

            //String id = null;
            List<CyberCampusEntity> cyberCampus = new ArrayList<>();
            for (Map<String, String> i : courseInfo) {
                // https://cyber.gachon.ac.kr/course/view.php?id=85455
                // courseId 추출 ex) 85455
                String courseId = getLastFiveDigits(i.get("link"));
                String link = i.get("link");
                String courseName = i.get("courseName");

                // courseId별로 class 생성
                CyberCampusEntity entity = new CyberCampusEntity(courseId, link, courseName);
                driver.get(link);
                Thread.sleep(1000); // 1초대기

                // 강좌의 링크를 이용해 과제(ok), mooc강의, 퀴즈, 공지사항 크롤링
                entity = getEntity(entity, driver);
                cyberCampus.add(entity);
            }

            // List로 이루어진 class를 JSON으로 변환
            // Initialize an instance of Gson
            Gson gson = new Gson();
            String json = gson.toJson(cyberCampus);

            // WebDriver 닫고 함수 종료
            driver.close();
            //if (id == null) return "Fast:Forward";
            return json;

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during sleep", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during getting the web title", e);
        }
    }

    private static List<Map<String, String>> getCourseId(String html) {
        if (html == null || html.isEmpty()) return null;

        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("a.coursefullname");

        List<Map<String, String>> courseArray = new ArrayList<>();

        for (Element element : elements) {
            Map<String, String> courseInfo = new HashMap<>();
            String link = element.attr("href"); // get href attribute
            String courseName = element.text(); // get course name text

            // put values into map
            courseInfo.put("link", link);
            courseInfo.put("courseName", courseName);

            courseArray.add(courseInfo);
        }

        return courseArray;
    }

    private static String getLastFiveDigits(String url) {
        if (url == null || url.isEmpty()) return null;

        int index = url.lastIndexOf('=');
        if (index != -1 && index + 1 < url.length()) {
            String lastPart = url.substring(index + 1);
            if (lastPart.length() == 5 && lastPart.matches("\\d+")) {
                return lastPart;
            }
        }
        return null;
    }

    private static CyberCampusEntity getEntity(CyberCampusEntity entity, WebDriver driver) {
        String html = driver.getPageSource();
        if (html == null || html.isEmpty()) return null;
        Document doc = Jsoup.parse(html);

        /*================================================
         * assignment crawling (과제 크롤링)
         * 과제이름, 과제기간, 과제 제출여부
         ================================================*/

        List<CyberCampusEntity.CyberCampusAssignment> assignmentList = new ArrayList<>();
        Elements elements = doc.select(".modtype_assign .activityinstance");
        for (Element element : elements) {
            String link = element.select("a").attr("href"); // 과제 url
            String name = element.select(".instancename").text(); // 과제 이름
            String date = element.select(".displayoptions").text(); // 과제 기간

            // 과제 기간을 시작시간, 종료시간으로 쪼개기
            String[] splitDate = date.split("~");
            String start = splitDate[0].trim();
            String end = splitDate[1].trim();

            // 과제 상세 페이지로 이동
            driver.get(link);
            String x = driver.getPageSource();
            if (x == null || x.isEmpty()) return null;
            Document y = Jsoup.parse(x);

            // 과제 제출여부
            Element su = y.selectFirst(".submissionstatussubmitted"); // 과제를 제출했을 경우
            String submit = su != null ? su.text().trim() : "Not Found";

            Boolean isSubmit;
            // (Submitted for grading)|(제출 완료)
            if (submit.equals("Submitted for grading") || submit.equals("제출 완료")) {
                isSubmit = true;
            } else {
                isSubmit = false;
            }

            // 과제 class 생성
            CyberCampusEntity.CyberCampusAssignment assignment = new CyberCampusEntity.CyberCampusAssignment(
                    name, start, end, isSubmit
            );
            assignmentList.add(assignment);
        }
        entity.setAssignments(assignmentList);


        /*================================================
         * MOOC crawling (mooc강의 크롤링)
         * 이름, 기간, 수강여부
         ================================================*/
        List<CyberCampusEntity.CyberCampusMOOC> moocList = new ArrayList<>();
        elements = doc.select(".activity.vod .activityinstance");
        for (Element element : elements) {
            String name = element.select(".instancename").text(); // 이름
            String date = element.select(".displayoptions .text-ubstrap").text().trim(); // 기간

            // 기간을 시작시간, 종료시간으로 쪼개기
            String[] splitDate = date.split("~");
            String start = splitDate[0].trim();
            String end = splitDate[1].trim();

            /*
            * 수강여부
            driver.get("https://cyber.gachon.ac.kr/report/ubcompletion/user_progress.php?id="+entity.getCourseId());
            Elements x = doc.select("td.text-left button");
            Boolean isSubmit;
            // (Submitted for grading)|(수ㄱ 완료)
            if (submit.equals("Submitted for grading") || submit.equals("제출 완료")) {
                isSubmit = true;
            } else {
                isSubmit = false;
            }
             */

            // mooc class 생성
            CyberCampusEntity.CyberCampusMOOC mooc = new CyberCampusEntity.CyberCampusMOOC(
                    name, start, end, true
            );
            moocList.add(mooc);
        }
        entity.setMoocs(moocList);


        /*================================================
         * Quiz crawling (퀴즈 크롤링)
         * 이름, 기간, 완료여부
         ================================================*/
        List<CyberCampusEntity.CyberCampusQuiz> quizList = new ArrayList<>();
        driver.get("https://cyber.gachon.ac.kr/mod/quiz/index.php?id="+entity.getCourseId());
        String q = driver.getPageSource();
        if (q == null || q.isEmpty()) return null;
        Document qd = Jsoup.parse(q);
        elements = qd.select("table.generaltable.table.table-bordered tbody tr");
        for (Element element : elements) {
            String link = element.select("td.cell.c1 a").attr("href").trim(); // 퀴즈 상세링크
            String name = element.select("td.cell.c1 a").text(); // 이름
            String end = element.select("td.cell.c2").text().trim(); // 종료시각

            // 완료여부
            driver.get("https://cyber.gachon.ac.kr/mod/quiz/"+link);
            Elements x = doc.select("table.generaltable.quizattemptsummary.table.table-bordered");
            Boolean isFinish;
            if (!x.isEmpty()) isFinish = true;
            else isFinish = false;

            // mooc class 생성
            CyberCampusEntity.CyberCampusQuiz quiz = new CyberCampusEntity.CyberCampusQuiz(
                    name, end, isFinish
            );
            quizList.add(quiz);
        }
        entity.setQuizzes(quizList);


        /*================================================
         * Announcement crawling (공지사항 크롤링)
         * 링크, 제목, 작성자, 작성시간, 조회
         ================================================*/
        List<CyberCampusEntity.CyberCampusAnnouncement> announcementList = new ArrayList<>();
        String annLink = doc.selectFirst(".course-content #section-0 .content .activityinstance a").attr("href");
        driver.get(annLink);
        q = driver.getPageSource();
        if (q == null || q.isEmpty()) return null;
        qd = Jsoup.parse(q);
        elements = qd.select(".ubboard_table.table.table-hover tbody tr");
        if (elements.size() == 1 && elements.get(0).select("td").attr("colspan").equals("5"))
            entity.setAnnouncements(announcementList);
        else {
            for (Element element : elements) {
                String link = element.select("td a").attr("href").trim(); // 공지 상세링크
                String name = element.select("td a").text(); // 제목
                Elements tmp = element.select("td.tcenter");
                String writer = tmp.get(tmp.size()-3).text().trim(); // 작성자
                String uploadTime = tmp.get(tmp.size()-2).text().trim(); // 작성시각
                String hit = tmp.get(tmp.size()-1).text().trim(); // 조회수

                // mooc class 생성
                CyberCampusEntity.CyberCampusAnnouncement announcement = new CyberCampusEntity.CyberCampusAnnouncement(
                        link, name, writer, uploadTime, Integer.parseInt(hit)
                );
                announcementList.add(announcement);
            }
            entity.setAnnouncements(announcementList);
        }


        return entity;
    }
}


