package com.gachon.hypergachon.advertise.controller;

import com.gachon.hypergachon.advertise.dto.CreateAdReq;
import com.gachon.hypergachon.advertise.service.AdvertiseService;
import com.gachon.hypergachon.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/advertise")
public class AdvertiseController {

    private final AdvertiseService advertiseService;

    @GetMapping("/test")
    public BaseResponseDto<String> testApi(){
        return new BaseResponseDto<>("test ok");
    }

    @PostMapping("")
    public BaseResponseDto<Long> createAd(@RequestBody CreateAdReq createAdReq){
        System.out.println(createAdReq.getName());
        return new BaseResponseDto<>(advertiseService.createAd(createAdReq));
    }

}
