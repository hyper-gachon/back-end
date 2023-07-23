package com.gachon.hypergachon.domain.advertise.service;

import com.gachon.hypergachon.domain.advertise.dto.CreateAdReq;
import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import com.gachon.hypergachon.domain.advertise.repository.AdvertiseRepository;
import com.gachon.hypergachon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
@RequiredArgsConstructor
@Service
public class AdvertiseService {
    private final AdvertiseRepository advertiseRepository;
    private final UserRepository userRepository;

    public Long createAd(User user, CreateAdReq createAdReq) {
//        System.out.println("pw"+user.getPassword());
        System.out.println("name "+user.getUsername());

//        User findUser = userRepository.findById(user.getU()).get();

//        Advertise advertise = Advertise.builder()
//                .user(findUser)
//                .title(createAdReq.getTitle())
//                .content(createAdReq.getContent())
//                .startDate(createAdReq.getStartDate())
//                .endDate(createAdReq.getEndDate())
//                .latitude(createAdReq.getLatitude())
//                .longitude(createAdReq.getLongitude())
//                .build();
//        System.out.println(advertise.getTitle());
//        Advertise createAdvertise = advertiseRepository.save(advertise);

//        return createAdvertise.getId();
        return (long) 1d;
    }
}
