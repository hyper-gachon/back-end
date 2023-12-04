package com.gachon.hypergachon.domain.location.service;

import com.gachon.hypergachon.domain.location.dto.response.GetBuildingRes;
import com.gachon.hypergachon.global.building.Building;
import com.gachon.hypergachon.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    public GetBuildingRes getBuildingNameByGPS(Double latitude, Double longitude) {
        Building building;
        try {
            building = Building.getBuilding(latitude, longitude);
        } catch(BusinessException e) {
            building = Building.getClosestBuilding(latitude, longitude);
        }
        return GetBuildingRes.of(building.getName());
    }
}
