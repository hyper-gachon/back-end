package com.gachon.hypergachon.domain.advertise.service;

import com.gachon.hypergachon.domain.advertise.dto.req.CreateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.req.UpdateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.res.CreateAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.dto.res.DeleteAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.dto.res.GetAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import com.gachon.hypergachon.domain.advertise.repository.AdvertiseRepository;
import com.gachon.hypergachon.domain.location.dto.response.GetBuildingRes;
import com.gachon.hypergachon.domain.user.repository.UserRepository;
import com.gachon.hypergachon.global.building.Building;
import com.gachon.hypergachon.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gachon.hypergachon.global.response.ErrorMessage.*;


@Transactional
@RequiredArgsConstructor
@Service
public class AdvertiseService {
    private final AdvertiseRepository advertiseRepository;
    private final UserRepository userRepository;

    // 광고 제작 탭
    public CreateAdvertiseResDto createAd(User user, CreateAdvertiseReq createAdvertiseReq) {

        Building building = getBuildingNameByGPS(createAdvertiseReq.getLatitude(), createAdvertiseReq.getLongitude());

        Advertise advertise = Advertise.builder()
                .user(userRepository.findById(Long.valueOf(user.getUsername())).get())
                .title(createAdvertiseReq.getTitle())
                .content(createAdvertiseReq.getContent())
                .startDate(createAdvertiseReq.getStartDate())
                .endDate(createAdvertiseReq.getEndDate())
                .latitude(createAdvertiseReq.getLatitude())
                .longitude(createAdvertiseReq.getLongitude())
                .building(building)
                .build();

        Advertise createAdvertise = advertiseRepository.save(advertise);

        return CreateAdvertiseResDto.of(createAdvertise.getId());
    }

    public List<GetAdvertiseResDto> findAll(User user) {
        List<Advertise> advertiseList = advertiseRepository.findAllByOrderByCreatedDateDesc();
        return advertiseList.stream()
                .map(GetAdvertiseResDto::of)
                .collect(Collectors.toList());
    }

    // 유저 올린 광고 찾기
    public ArrayList<GetAdvertiseResDto> findByUserId(User user){
        List<Advertise> advertiseList = advertiseRepository.findByUserOrderByCreatedDateDesc(userRepository.findById(Long.parseLong(user.getUsername())).get());
        ArrayList<GetAdvertiseResDto> getAdvertiseResDtoList = new ArrayList<>();

        for (Advertise advertise : advertiseList) {
            GetAdvertiseResDto getAdvertiseResDto =
                    GetAdvertiseResDto.of(
                            advertise.getId(),
                            advertise.getTitle(),
                            advertise.getContent(),
                            advertise.getStartDate(),
                            advertise.getEndDate(),
                            advertise.getLatitude(),
                            advertise.getLongitude());

            getAdvertiseResDtoList.add(getAdvertiseResDto);
           }
        return getAdvertiseResDtoList;
    }

    // 유저 올린 광고 삭제
    public DeleteAdvertiseResDto deleteAdvertise(User user, Long postId){
        Advertise advertise = getAdvertiseById(postId);

        // 해당 포스트를 작성한 유저인지 확인
        checkAdvertiseUser(advertise, Long.parseLong(user.getUsername()));

        advertiseRepository.deleteById(postId);

        return DeleteAdvertiseResDto.of(true);
    }


    public GetAdvertiseResDto updateAdvertise(User user, UpdateAdvertiseReq updateAdvertiseReq){

        Advertise advertise = getAdvertiseById(updateAdvertiseReq.getPostId());

        // 해당 포스트를 작성한 유저인지 확인
        checkAdvertiseUser(advertise, Long.parseLong(user.getUsername()));
        Building building = getBuildingNameByGPS(updateAdvertiseReq.getLatitude(), updateAdvertiseReq.getLongitude());
        advertise.update(
                updateAdvertiseReq.getTitle(),
                updateAdvertiseReq.getContent(),
                updateAdvertiseReq.getStartDate(),
                updateAdvertiseReq.getEndDate(),
                updateAdvertiseReq.getLatitude(),
                updateAdvertiseReq.getLongitude(),
                building
        );

        return GetAdvertiseResDto.of(advertise);
    }

    public GetAdvertiseResDto getAdvertiseDetail(Long postId) {
        Advertise advertise = getAdvertiseById(postId);
        return GetAdvertiseResDto.of(advertise);
    }

    private Advertise getAdvertiseById(Long postId) {
        return advertiseRepository.findById(postId)
                .orElseThrow(() ->  new BusinessException(ADVERTISE_NOT_FOUND));
    }

    // 해당 포스트를 작성한 유저인지 확인
    private void checkAdvertiseUser(Advertise advertise, Long userId) {
        if(advertise.getUser().getId() != userId)
            throw new BusinessException(WRITER_NOT_MATCH);
    }

    private Building getBuildingNameByGPS(Double latitude, Double longitude) {
        Building building;
        try {
            building = Building.getBuilding(latitude, longitude);
        } catch(BusinessException e) {
            building = Building.getClosestBuilding(latitude, longitude);
        }
        return building;
    }
}
