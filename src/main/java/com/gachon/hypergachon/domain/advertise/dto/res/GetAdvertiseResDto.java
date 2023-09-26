package com.gachon.hypergachon.domain.advertise.dto.res;

import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAdvertiseResDto {
    private Long postId;

    private String title;

    private String content;

    private String startDate;

    private String endDate;

    private Double latitude;

    private Double longitude;


    public static GetAdvertiseResDto of(Long postId,
                                        String title,
                                        String content,
                                        String startDate,
                                        String endDate,
                                        Double latitude,
                                        Double longitude) {
        return GetAdvertiseResDto.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static GetAdvertiseResDto of(Advertise advertise) {
        return GetAdvertiseResDto.builder()
                .postId(advertise.getId())
                .title(advertise.getTitle())
                .content(advertise.getContent())
                .startDate(advertise.getStartDate())
                .endDate(advertise.getEndDate())
                .latitude(advertise.getLatitude())
                .longitude(advertise.getLongitude())
                .build();
    }
}
