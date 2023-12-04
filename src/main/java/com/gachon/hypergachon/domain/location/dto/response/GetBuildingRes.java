package com.gachon.hypergachon.domain.location.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBuildingRes {

    private String buildingName;

    public static GetBuildingRes of(String buildingName) {
        return GetBuildingRes.builder()
                .buildingName(buildingName)
                .build();
    }
}
