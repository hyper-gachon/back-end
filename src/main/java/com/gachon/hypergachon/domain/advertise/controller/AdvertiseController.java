package com.gachon.hypergachon.domain.advertise.controller;

import com.gachon.hypergachon.domain.advertise.dto.CreateAdReq;
import com.gachon.hypergachon.domain.advertise.service.AdvertiseService;
import com.gachon.hypergachon.response.BaseResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/advertises")
public class AdvertiseController {

    private final AdvertiseService advertiseService;

    @GetMapping("/test")
    public BaseResponseDto<String> testApi(){
        return new BaseResponseDto<>("test ok");
    }

    @PostMapping("")
    public BaseResponseDto<Long> createAd(@AuthenticationPrincipal User user, @RequestBody CreateAdReq createAdReq){
        return new BaseResponseDto<>(advertiseService.createAd(user, createAdReq));
    }

}
