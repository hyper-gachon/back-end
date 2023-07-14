package com.gachon.hypergachon.crawling.service;

import com.beust.ah.A;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gachon.hypergachon.crawling.dto.AcademicScheduleDto;
import lombok.Getter;
import lombok.Setter;
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

    public String getAcademicSchedule(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String month = doc.selectFirst(".sche-alb h3").text();
            Elements acaScheduleDate = doc.select("table tbody tr th");
            Elements acaSchedule = doc.select(".sche-comt table tbody tr td");

            ArrayList<AcademicScheduleDto> academicSchedule = new ArrayList<>();
            for (int i=0; i<acaScheduleDate.size(); i++) {
                AcademicScheduleDto dto = new AcademicScheduleDto();
                dto.setAcaScheduleDate(acaScheduleDate.get(i).text());
                dto.setAcaSchedule(acaSchedule.get(i).text());
                academicSchedule.add(dto);
            }

            AcademicCalendar calendar = new AcademicCalendar();
            calendar.setMonth(month);
            calendar.setAcademicSchedule(academicSchedule);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(calendar);

            return json;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during getting the web title", e);
        }
    }

    @Getter
    @Setter
    public class AcademicCalendar {
        private String month;
        private List<AcademicScheduleDto> academicSchedule;

        // 생성자, getter, setter 생략...
    }
}
