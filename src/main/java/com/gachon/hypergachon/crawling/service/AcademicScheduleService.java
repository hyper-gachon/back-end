package com.gachon.hypergachon.crawling.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AcademicScheduleService {

    public static class WebResult {
        public String month; // 년월
        public List<String> acaScheduleDate; // 스케줄 날짜
        public List<String> acaSchedule; // 스케줄 제목

        public WebResult(String month, List<String> acaScheduleDate, List<String> acaSchedule) {
            this.month = month;
            this.acaScheduleDate = acaScheduleDate;
            this.acaSchedule = acaSchedule;
        }
    }

    public WebResult getAcademicSchedule(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String month = doc.selectFirst(".sche-alb h3").text();
            Elements acaScheduleDate = doc.select("table tbody tr th");
            Elements acaSchedule = doc.select(".sche-comt table tbody tr td");

            List<String> acaScheduleDateList = new ArrayList<>();
            for (Element i : acaScheduleDate) {
                acaScheduleDateList.add(i.text());
            }
            List<String> acaScheduleList = new ArrayList<>();
            for (Element i : acaSchedule) {
                acaScheduleList.add(i.text());
            }

            return new WebResult(month, acaScheduleDateList, acaScheduleList);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during getting the web title", e);
        }
    }
}
