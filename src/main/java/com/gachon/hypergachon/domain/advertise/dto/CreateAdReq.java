package com.gachon.hypergachon.domain.advertise.dto;

import lombok.Getter;

@Getter
public class CreateAdReq {
    Long id;
    String title;
    String content;
    String startDate;
    String endDate;
    Double latitude;
    Double longitude;
}
