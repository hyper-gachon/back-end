package com.gachon.hypergachon.domain.advertise.dto.req;

import lombok.Getter;

@Getter
public class CreateAdvertiseReq {
    private Long userId;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private Double latitude;
    private Double longitude;
}
