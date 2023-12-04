package com.gachon.hypergachon.domain.location.dto.response;

import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAroundAdvertisementRes {

    private String location;

    private List<GetAdvertiseRes> advertises;

    public static GetAroundAdvertisementRes of(String location, List<Advertise> advertises) {
        return GetAroundAdvertisementRes.builder()
                .location(location)
                .advertises(advertises.stream()
                        .map(GetAdvertiseRes::of)
                        .toList())
                .build();
    }
}
