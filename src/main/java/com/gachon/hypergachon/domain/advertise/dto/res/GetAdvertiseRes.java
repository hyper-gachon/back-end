package com.gachon.hypergachon.domain.advertise.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAdvertiseRes {
    private Long postId;

    private String title;

    private String content;

    private String startDate;

    private String endDate;

    private Double latitude;

    private Double longitude;


    public static GetAdvertiseRes of(Long postId,
                                     String title,
                                     String content,
                                     String startDate,
                                     String endDate,
                                     Double latitude,
                                     Double longitude) {
        return GetAdvertiseRes.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
