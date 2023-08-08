package com.gachon.hypergachon.domain.advertise.service;

import com.gachon.hypergachon.domain.advertise.dto.req.CreateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.req.UpdateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.res.GetAdvertiseRes;
import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import com.gachon.hypergachon.domain.advertise.repository.AdvertiseRepository;
import com.gachon.hypergachon.domain.user.repository.UserRepository;
import com.gachon.hypergachon.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gachon.hypergachon.response.ErrorMessage.*;


@Transactional
@RequiredArgsConstructor
@Service
public class AdvertiseService {
    private final AdvertiseRepository advertiseRepository;
    private final UserRepository userRepository;

    // 광고 제작 탭
    public Long createAd(User user, CreateAdvertiseReq createAdvertiseReq) {
        // 보낸 사용자 id와 jwt토큰상의 유저 id 정보가 다를 경우 실행
        if(!user.getUsername().equals(createAdvertiseReq.getUserId().toString()))
            throw new BusinessException(USER_JWT_INFORMATION_NO_CORRECT);

        Advertise advertise = Advertise.builder()
                .user(userRepository.findById(createAdvertiseReq.getUserId()).get())
                .title(createAdvertiseReq.getTitle())
                .content(createAdvertiseReq.getContent())
                .startDate(createAdvertiseReq.getStartDate())
                .endDate(createAdvertiseReq.getEndDate())
                .latitude(createAdvertiseReq.getLatitude())
                .longitude(createAdvertiseReq.getLongitude())
                .build();

        Advertise createAdvertise = advertiseRepository.save(advertise);

        return createAdvertise.getId();
    }

    // 유저 올린 광고 찾기
    public ArrayList<GetAdvertiseRes> findByUserId(User user){
        List<Advertise> advertiseList = userRepository.findById(Long.parseLong(user.getUsername())).get().getAdvertises();
        ArrayList<GetAdvertiseRes> getAdvertiseResList = new ArrayList<>();

        for (Advertise advertise : advertiseList) {
            GetAdvertiseRes getAdvertiseRes = GetAdvertiseRes.builder()
                    .id(advertise.getId())
                    .title(advertise.getTitle())
                    .content(advertise.getContent())
                    .startDate(advertise.getStartDate())
                    .endDate(advertise.getEndDate())
                    .latitude(advertise.getLatitude())
                    .longitude(advertise.getLongitude())
                    .build();

            getAdvertiseResList.add(getAdvertiseRes);
           }
        return getAdvertiseResList;
    }

    // 유저 올린 광고 삭제
    public Boolean deleteAdvertise(User user, Long postId){
        Optional<Advertise> advertise = advertiseRepository.findById(postId);

        // 해당 광고가 존재하는지 확인
        if(!advertise.isPresent())
            throw new BusinessException(ADVERTISE_NOT_FOUND);

        // 해당 포스트를 작성한 유저인지 확인
        checkAdvertiseUser(advertise, Long.parseLong(user.getUsername()));

        advertiseRepository.deleteById(postId);

        return true;
    }


    public GetAdvertiseRes updateAdvertise(User user, UpdateAdvertiseReq updateAdvertiseReq){

        Optional<Advertise> advertise = advertiseRepository.findById(updateAdvertiseReq.getPostId());

        // 해당 광고가 존재하는지 확인
        if(!advertise.isPresent())
            throw new BusinessException(ADVERTISE_NOT_FOUND);

        // 해당 포스트를 작성한 유저인지 확인
        checkAdvertiseUser(advertise, Long.parseLong(user.getUsername()));

        Advertise ad = advertise.get();

        ad.update(
                updateAdvertiseReq.getTitle(),
                updateAdvertiseReq.getContent(),
                updateAdvertiseReq.getStartDate(),
                updateAdvertiseReq.getEndDate(),
                updateAdvertiseReq.getLatitude(),
                updateAdvertiseReq.getLongitude()
        );

        GetAdvertiseRes getAdvertiseRes = GetAdvertiseRes.builder()
                .id(ad.getId())
                .title(ad.getTitle())
                .content(ad.getContent())
                .startDate(ad.getStartDate())
                .endDate(ad.getEndDate())
                .latitude(ad.getLatitude())
                .longitude(ad.getLongitude())
                .build();

        return getAdvertiseRes;
    }

    // 해당 포스트를 작성한 유저인지 확인
    public void checkAdvertiseUser(Optional<Advertise> advertise, Long userId) {
        if(advertise.get().getUser().getId() != userId)
            throw new BusinessException(WRITER_NOT_MATCH);
    }
}
