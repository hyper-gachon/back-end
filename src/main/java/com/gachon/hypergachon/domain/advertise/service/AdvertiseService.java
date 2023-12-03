package com.gachon.hypergachon.domain.advertise.service;

import com.gachon.hypergachon.domain.advertise.dto.req.CreateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.req.UpdateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.res.CreateAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.dto.res.DeleteAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.dto.res.GetAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import com.gachon.hypergachon.domain.advertise.repository.AdvertiseRepository;
import com.gachon.hypergachon.domain.user.repository.UserRepository;
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

        Advertise advertise = Advertise.builder()
                .user(userRepository.findById(Long.valueOf(user.getUsername())).get())
                .title(createAdvertiseReq.getTitle())
                .content(createAdvertiseReq.getContent())
                .startDate(createAdvertiseReq.getStartDate())
                .endDate(createAdvertiseReq.getEndDate())
                .latitude(createAdvertiseReq.getLatitude())
                .longitude(createAdvertiseReq.getLongitude())
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
        Optional<Advertise> advertise = advertiseRepository.findById(postId);

        // 해당 광고가 존재하는지 확인
        if(!advertise.isPresent())
            throw new BusinessException(ADVERTISE_NOT_FOUND);

        // 해당 포스트를 작성한 유저인지 확인
        checkAdvertiseUser(advertise, Long.parseLong(user.getUsername()));

        advertiseRepository.deleteById(postId);

        return DeleteAdvertiseResDto.of(true);
    }


    public GetAdvertiseResDto updateAdvertise(User user, UpdateAdvertiseReq updateAdvertiseReq){

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

        return GetAdvertiseResDto.of(ad.getId(), ad.getTitle(), ad.getContent(), ad.getStartDate(), ad.getEndDate(),ad.getLatitude(),ad.getLongitude());
    }

    // 해당 포스트를 작성한 유저인지 확인
    public void checkAdvertiseUser(Optional<Advertise> advertise, Long userId) {
        if(advertise.get().getUser().getId() != userId)
            throw new BusinessException(WRITER_NOT_MATCH);
    }
}
