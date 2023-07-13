package com.gachon.hypergachon.crawling.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class FullNoticeService {

    public static class WebResult {
        public List<String> noticeNum; // 공지 No
        public List<String> noticeList; // 공지 제목
        public List<String> writerList; // 작성자
        public List<String> dateList; // 날짜

        public WebResult(List<String> noticeNum, List<String> noticeList, List<String> writerList,List<String> dateList) {
            this.noticeNum = noticeNum;
            this.noticeList = noticeList;
            this.writerList = writerList;
            this.dateList = dateList;
        }
        /*
        링크는 가져오지 않습니다.
        현재 가천대학교 공지사항의 세부 글 링크는 다음과 같이 작성되어 있습니다.
        <a href="javascript:jf_viewArtcl('kor', '87956');">
		    <strong>[취업소식] [히든챔피언 채용공고] 일신테크놀로지 생산(자재)관리/생산기술 부문 추천채용 (~7/16)</strong>
        </a>
        이 경우 jf_viewArtcl이라는 함수를 호출하여 웹페이지를 로드하기 때문에 일반적인 크롤링으로는 링크 수집이 불가능합니다.

        해결 방법을 selenium으로 직접 웹페이지에 접속하여 링크를 얻어오는 방법이 있는데,
        직접 접속할 경우 안전성을 위해 시간이 소요됩니다.
        서버에서 시간 지연이 있는 것 보다는 client에서 제목을 기반으로 주소를 찾아오는 게 나을 듯 하여,
        백엔드에서는 주소를 받아오지 않는 것으로 결정했습니다.
         */
    }

    public WebResult getFullNotice(String url) {
        try {
            Document doc = Jsoup.connect(url).get(); // html 가져오기

            // 필요한 정보 parsing
            Elements numTable = doc.select("table tbody tr td.td-num");
            Elements noticesTable = doc.select("table tbody tr td.td-subject a strong");
            Elements writerTable = doc.select("table tbody tr td.td-write");
            Elements dateTable = doc.select("table tbody tr td.td-date");

            // parsing한 데이터를 각 List에 저장
            List<String> noticeNum = new ArrayList<>();
            for (Element num : numTable) {
                noticeNum.add(num.text());
            }
            List<String> noticeList = new ArrayList<>();
            for (Element notice : noticesTable) {
                noticeList.add(notice.text());
            }
            List<String> writerList = new ArrayList<>();
            for (Element writer : writerTable) {
                writerList.add(writer.text());
            }
            List<String> dateList = new ArrayList<>();
            for (Element date : dateTable) {
                dateList.add(date.text());
            }

            return new WebResult(noticeNum, noticeList, writerList, dateList);

        } catch (IOException e) {
            throw new RuntimeException("Error during getting the web title", e);
        }
    }
}
