package com.gachon.hypergachon.domain.location.controller;

import com.gachon.hypergachon.domain.location.dto.response.GetBuildingRes;
import com.gachon.hypergachon.domain.location.service.LocationService;
import com.gachon.hypergachon.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/building")
    public BaseResponseDto<GetBuildingRes> getBuildingByGPS(@RequestParam Double latitude, @RequestParam Double longitude) {
        return new BaseResponseDto<>(locationService.getBuildingNameByGPS(latitude, longitude));
    }
}
