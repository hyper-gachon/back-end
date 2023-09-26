package com.gachon.hypergachon.domain.crawling.controller;

import com.gachon.hypergachon.domain.crawling.service.CyberCampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CyberCampusController {

    private final CyberCampusService cyberCampusService;

    @Autowired
    public CyberCampusController(CyberCampusService webCrawlingService) {
        this.cyberCampusService = webCrawlingService;
    }

    @GetMapping("api/crawling/cybercampus")
    public ResponseEntity<String> crawlWeb() {
        /*
        getCyberCampus는 selenium으로 웹페이지를 직접 열어 가져오기 때문에,
        주기적으로 or 요청 시마다 getCyberCampus()를 호출해야 함
         */
        Optional<String> result = Optional.ofNullable(cyberCampusService.getCyberCampus());
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

