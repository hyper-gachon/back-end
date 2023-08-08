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
}
