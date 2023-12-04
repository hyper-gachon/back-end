package com.gachon.hypergachon.domain.advertise.controller;

import com.gachon.hypergachon.domain.advertise.dto.req.CreateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.req.UpdateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.res.CreateAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.dto.res.DeleteAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.dto.res.GetAdvertiseResDto;
import com.gachon.hypergachon.domain.advertise.service.AdvertiseService;
import com.gachon.hypergachon.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/advertises")
public class AdvertiseController {

    private final AdvertiseService advertiseService;

    @GetMapping("/test")
    public BaseResponseDto<String> testApi(){
        return new BaseResponseDto<>("test ok");
    }


    // 광고 제작 API
    @PostMapping("")
    public BaseResponseDto<CreateAdvertiseResDto> createAd(@AuthenticationPrincipal User user, @RequestBody CreateAdvertiseReq createAdvertiseReq){
        return new BaseResponseDto<>(advertiseService.createAd(user, createAdvertiseReq));
    }

    // 전체 광고 조회
    @GetMapping("/all")
    public BaseResponseDto<List<GetAdvertiseResDto>> findAllAdvertise(@AuthenticationPrincipal User user) {
        List<GetAdvertiseResDto> advertiseList = advertiseService.findAll(user);
        return new BaseResponseDto<>(advertiseList);
    }

    // 유저 업로드한 광고 검색 API
    @GetMapping("")
    public BaseResponseDto<ArrayList<GetAdvertiseResDto>> getUserAd(@AuthenticationPrincipal User user) {
        ArrayList<GetAdvertiseResDto> advertiseList = advertiseService.findByUserId(user);
        return new BaseResponseDto<>(advertiseList);
    }

    // 업로드한 광고 삭제 API
    @DeleteMapping("/{postId}")
    public BaseResponseDto<DeleteAdvertiseResDto> deleteAd(@AuthenticationPrincipal User user, @PathVariable Long postId){
        return new BaseResponseDto<>(advertiseService.deleteAdvertise(user, postId));
    }

    // 업로드한 광고 수정 API
    @PatchMapping("")
    public BaseResponseDto<GetAdvertiseResDto> updateAd(@AuthenticationPrincipal User user, @RequestBody UpdateAdvertiseReq updateAdvertiseReq){
        return new BaseResponseDto<>(advertiseService.updateAdvertise(user, updateAdvertiseReq));
    }

    @GetMapping("/{postId}")
    public BaseResponseDto<GetAdvertiseResDto> getAdvertiseDetail(@PathVariable Long postId) {
        return new BaseResponseDto<>(advertiseService.getAdvertiseDetail(postId));
    }

}
