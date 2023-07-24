package com.gachon.hypergachon.domain.crawling.controller;

import com.gachon.hypergachon.domain.crawling.service.FullNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FullNoticeController {

    private final FullNoticeService fullNoticeService;

    @Autowired
    public FullNoticeController(FullNoticeService webCrawlingService) {
        this.fullNoticeService = webCrawlingService;
    }

    @GetMapping("/crawling/fullnotice")
    public ResponseEntity<String> crawlWeb() {
        Optional<String> result = Optional.ofNullable(fullNoticeService.getFullNotice("https://www.gachon.ac.kr/kor/7986/subview.do"));
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
