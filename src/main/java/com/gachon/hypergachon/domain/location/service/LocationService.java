package com.gachon.hypergachon.domain.location.service;

import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import com.gachon.hypergachon.domain.advertise.repository.AdvertiseRepository;
import com.gachon.hypergachon.domain.location.dto.response.GetAroundAdvertisementRes;
import com.gachon.hypergachon.domain.location.dto.response.GetBuildingRes;
import com.gachon.hypergachon.global.building.Building;
import com.gachon.hypergachon.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationService {

    private final AdvertiseRepository advertiseRepository;

    public GetBuildingRes getBuildingNameByGPS(Double latitude, Double longitude) {
        Building building = getBuildingFromGPS(latitude, longitude);
        return GetBuildingRes.of(building.getName());
    }

    public GetAroundAdvertisementRes getAroundAdvertisements(Double latitude, Double longitude) {
        Building building = getBuildingFromGPS(latitude, longitude);
        LocalDate now = LocalDate.now();
        List<Advertise> advertises = advertiseRepository.findAllByStartDateBeforeAndEndDateAfterAndBuildingEquals(now, now, building);
        return GetAroundAdvertisementRes.of(building.getName(), advertises);
    }

    private Building getBuildingFromGPS(Double latitude, Double longitude) {
        Building building;
        try {
            building = Building.getBuilding(latitude, longitude);
        } catch(BusinessException e) {
            building = Building.getClosestBuilding(latitude, longitude);
        }
        return building;
    }
}
