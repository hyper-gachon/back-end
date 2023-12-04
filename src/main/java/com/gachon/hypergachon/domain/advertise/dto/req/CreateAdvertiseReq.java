package com.gachon.hypergachon.domain.advertise.dto.req;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class CreateAdvertiseReq {
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;
    private Double latitude;
    private Double longitude;
}
