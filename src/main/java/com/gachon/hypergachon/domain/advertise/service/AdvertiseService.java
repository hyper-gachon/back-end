package com.gachon.hypergachon.domain.advertise.service;

import com.gachon.hypergachon.domain.advertise.dto.CreateAdReq;
import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import com.gachon.hypergachon.domain.advertise.repository.AdvertiseRepository;
import com.gachon.hypergachon.domain.user.repository.UserRepository;
import com.gachon.hypergachon.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gachon.hypergachon.response.ErrorMessage.USER_JWT_INFORMATION_NO_CORRECT;


@Transactional
@RequiredArgsConstructor
@Service
public class AdvertiseService {
    private final AdvertiseRepository advertiseRepository;
    private final UserRepository userRepository;

    // 광고 제작 탭
    public Long createAd(User user, CreateAdReq createAdReq) {

        // 보낸 사용자 id와 jwt토큰상의 유저 id 정보가 다를 경우 실행
        if(!user.getUsername().equals(createAdReq.getId()))
            throw new BusinessException(USER_JWT_INFORMATION_NO_CORRECT);

        Advertise advertise = Advertise.builder()
                .user(userRepository.findById(createAdReq.getId()).get())
                .title(createAdReq.getTitle())
                .content(createAdReq.getContent())
                .startDate(createAdReq.getStartDate())
                .endDate(createAdReq.getEndDate())
                .latitude(createAdReq.getLatitude())
                .longitude(createAdReq.getLongitude())
                .build();

        Advertise createAdvertise = advertiseRepository.save(advertise);

        return createAdvertise.getId();
    }
}
