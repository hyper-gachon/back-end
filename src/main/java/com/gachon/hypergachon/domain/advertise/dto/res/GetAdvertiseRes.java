package com.gachon.hypergachon.domain.advertise.dto.res;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class GetAdvertiseRes {
    private Long id;

    private String title;

    private String content;

    private String startDate;

    private String endDate;

    private Double latitude;

    private Double longitude;


    public static GetAdvertiseRes of(Long id,
                                     String title,
                                     String content,
                                     String startDate,
                                     String endDate,
                                     Double latitude,
                                     Double longitude) {
        return GetAdvertiseRes.builder()
                .id(id)
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
