package com.gachon.hypergachon.domain.user.controller;

import com.gachon.hypergachon.domain.user.dto.request.EmailCheckDto;
import com.gachon.hypergachon.domain.user.dto.request.LoginReqDto;
import com.gachon.hypergachon.domain.user.dto.response.LoginResDto;
import com.gachon.hypergachon.domain.user.service.EmailService;
import com.gachon.hypergachon.response.BaseResponseDto;
import com.gachon.hypergachon.domain.user.dto.request.EmailReqDto;
import com.gachon.hypergachon.domain.user.dto.request.UserDto;
import com.gachon.hypergachon.domain.user.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final EmailService emailService;
    private final UserService userService;

    // email 인증 코드 받는
    @GetMapping("/send-emails")
    public BaseResponseDto<String> sendEmail(@RequestBody EmailReqDto emailReqDto) throws MessagingException, UnsupportedEncodingException {
        return new BaseResponseDto<>(emailService.sendEmail(emailReqDto.getEmail()));
    }

    @GetMapping("/check-emails")
    public BaseResponseDto<Boolean> checkEmailCode(@RequestBody EmailCheckDto emailCheckDto) {
        return new BaseResponseDto<>(emailService.checkEmailCode(emailCheckDto));
    }

    @GetMapping("/login")
    public BaseResponseDto<LoginResDto> login(@RequestBody LoginReqDto loginReqDto) {
        return new BaseResponseDto<>(userService.login(loginReqDto));
    }


    @PostMapping("/sign-in")
    public BaseResponseDto<String> signIn(@RequestBody UserDto userDto) {
        return new BaseResponseDto<>(userService.signIn(userDto));
    }
}
