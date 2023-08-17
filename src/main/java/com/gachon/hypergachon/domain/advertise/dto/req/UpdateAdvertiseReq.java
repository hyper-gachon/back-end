package com.gachon.hypergachon.domain.advertise.dto.req;


import lombok.Getter;

@Getter
public class UpdateAdvertiseReq {
    private Long postId;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private Double latitude;
    private Double longitude;
}
