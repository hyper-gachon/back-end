package com.gachon.hypergachon.crawling.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachon.hypergachon.crawling.dto.CyberCampusDto;
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

            String id = null;
            for (Map<String, String> i : courseInfo) {
                // https://cyber.gachon.ac.kr/course/view.php?id=85455
                // courseId 추출 ex) 85455
                String courseId = getLastFiveDigits(i.get("link"));

                // courseId별로 class 생성

            }


            // 강좌의 링크를 이용해 과제, mooc강의, 퀴즈, 공지사항 크롤링 + 1초대기(로딩)
            // CyberCampusDto 상속하는 class 생성 후 저장

            // JSON으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            //String json = objectMapper.writeValueAsString(fullNotice);

            // WebDriver 닫고 함수 종료
            driver.close();
            if (id == null) return "오 류";
            return id;

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

        return null; // 혹은 필요에 따라 적절한 에러 메시지를 반환
    }



}


