package com.gachon.hypergachon.advertise.dto;

import lombok.Getter;

@Getter
public class CreateAdReq {
    String title;
    String content;
    String name;
    String email;
    String startDate;
    String endDate;
    Double latitude;
    Double longitude;
}
