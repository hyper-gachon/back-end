package com.gachon.hypergachon.domain.crawling.controller;

import com.gachon.hypergachon.domain.crawling.service.AcademicScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AcademicScheduleController {

    private final AcademicScheduleService academicScheduleService;

    @Autowired
    public AcademicScheduleController(AcademicScheduleService webCrawlingService) {
        this.academicScheduleService = webCrawlingService;
    }

    @GetMapping("api/crawling/academic_schedule")
    public ResponseEntity<String> crawlWeb() {
        Optional<String> result = Optional.ofNullable(academicScheduleService.getAcademicSchedule("https://www.gachon.ac.kr/kor/1075/subview.do"));
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
