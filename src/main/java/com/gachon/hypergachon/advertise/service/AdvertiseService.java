package com.gachon.hypergachon.advertise.service;

import com.gachon.hypergachon.advertise.dto.CreateAdReq;
import com.gachon.hypergachon.advertise.entity.Advertise;
import com.gachon.hypergachon.advertise.repository.AdvertiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
@Service
public class AdvertiseService {
    private final AdvertiseRepository advertiseRepository;

    public Long createAd(CreateAdReq createAdReq) {
        Advertise advertise = Advertise.builder()
                .title(createAdReq.getTitle())
                .content(createAdReq.getContent())
                .name(createAdReq.getName())
                .email(createAdReq.getEmail())
                .startDate(createAdReq.getStartDate())
                .endDate(createAdReq.getEndDate())
                .latitude(createAdReq.getLatitude())
                .longitude(createAdReq.getLongitude())
                .build();
        System.out.println(advertise.getTitle());
        Advertise createAdvertise = advertiseRepository.save(advertise);

        return createAdvertise.getId();
    }
}
