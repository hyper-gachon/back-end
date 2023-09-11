package com.gachon.hypergachon.domain.advertise.controller;

import com.gachon.hypergachon.domain.advertise.dto.req.CreateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.req.UpdateAdvertiseReq;
import com.gachon.hypergachon.domain.advertise.dto.res.CreateAdvertiseRes;
import com.gachon.hypergachon.domain.advertise.dto.res.DeleteAdvertiseRes;
import com.gachon.hypergachon.domain.advertise.dto.res.GetAdvertiseRes;
import com.gachon.hypergachon.domain.advertise.service.AdvertiseService;
import com.gachon.hypergachon.global.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/advertises")
public class AdvertiseController {

    private final AdvertiseService advertiseService;

    @GetMapping("/test")
    public BaseResponseDto<String> testApi(){
        return new BaseResponseDto<>("test ok");
    }


    // 광고 제작 API
    @PostMapping("")
    public BaseResponseDto<CreateAdvertiseRes> createAd(@AuthenticationPrincipal User user, @RequestBody CreateAdvertiseReq createAdvertiseReq){
        return new BaseResponseDto<>(advertiseService.createAd(user, createAdvertiseReq));
    }

    // 유저 업로드한 광고 검색 API
    @GetMapping("")
    public BaseResponseDto<ArrayList<GetAdvertiseRes>> getUserAd(@AuthenticationPrincipal User user) {
        ArrayList<GetAdvertiseRes> advertiseList = advertiseService.findByUserId(user);
        return new BaseResponseDto<>(advertiseList);
    }

    // 업로드한 광고 삭제 API
    @DeleteMapping("/{postId}")
    public BaseResponseDto<DeleteAdvertiseRes> deleteAd(@AuthenticationPrincipal User user, @PathVariable Long postId){
        return new BaseResponseDto<>(advertiseService.deleteAdvertise(user, postId));
    }

    // 업로드한 광고 수정 API
    @PatchMapping("")
    public BaseResponseDto<GetAdvertiseRes> updateAd(@AuthenticationPrincipal User user, @RequestBody UpdateAdvertiseReq updateAdvertiseReq){
        return new BaseResponseDto<>(advertiseService.updateAdvertise(user, updateAdvertiseReq));
    }

}
