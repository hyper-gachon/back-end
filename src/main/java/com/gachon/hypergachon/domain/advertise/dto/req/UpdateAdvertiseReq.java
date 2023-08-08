package com.gachon.hypergachon.domain.advertise.dto.req;


import lombok.Getter;

@Getter
public class UpdateAdvertiseReq {
    Long postId;
    String title;
    String content;
    String startDate;
    String endDate;
    Double latitude;
    Double longitude;
}
