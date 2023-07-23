package com.gachon.hypergachon.domain.crawling.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachon.hypergachon.domain.crawling.dto.FullNoticeDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import org.springframework.stereotype.Service;


@Service
public class FullNoticeService {

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

    public String getFullNotice(String url) {
        try {
            Document doc = Jsoup.connect(url).get(); // html 가져오기

            // 필요한 정보 parsing
            Elements numTable = doc.select("table tbody tr td.td-num");
            Elements noticesTable = doc.select("table tbody tr td.td-subject a strong");
            Elements writerTable = doc.select("table tbody tr td.td-write");
            Elements dateTable = doc.select("table tbody tr td.td-date");

            // parsing한 데이터를 객체 리스트에 저장
            FullNoticeDto[] fullNotice = new FullNoticeDto[50];
            for (int i=0; i<50; i++) {
                fullNotice[i] = new FullNoticeDto();
                fullNotice[i].setNoticeNum(numTable.get(i).text());
                fullNotice[i].setNoticeTitle(noticesTable.get(i).text());
                fullNotice[i].setWriter(writerTable.get(i).text());
                fullNotice[i].setDate(dateTable.get(i).text());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(fullNotice);

            return json;

        } catch (IOException e) {
            throw new RuntimeException("Error during getting the web title", e);
        }
    }
}
