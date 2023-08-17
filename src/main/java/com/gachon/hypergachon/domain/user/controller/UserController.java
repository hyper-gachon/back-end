package com.gachon.hypergachon.domain.user.controller;

import com.gachon.hypergachon.domain.user.dto.request.EmailCheckDto;
import com.gachon.hypergachon.domain.user.dto.request.LoginReqDto;
import com.gachon.hypergachon.domain.user.dto.response.LoginResDto;
import com.gachon.hypergachon.domain.user.dto.request.RefreshTokenReqDto;
import com.gachon.hypergachon.domain.user.service.EmailService;
import com.gachon.hypergachon.response.BaseResponseDto;
import com.gachon.hypergachon.domain.user.dto.request.EmailReqDto;
import com.gachon.hypergachon.domain.user.dto.request.UserDto;
import com.gachon.hypergachon.domain.user.service.UserService;
import com.gachon.hypergachon.security.dto.TokenDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public BaseResponseDto<LoginResDto> login(@RequestBody LoginReqDto loginReqDto, HttpServletResponse response) {
        return new BaseResponseDto<>(userService.login(loginReqDto, response));
    }


    @PostMapping("/sign-in")
    public BaseResponseDto<String> signIn(@RequestBody UserDto userDto) {
        return new BaseResponseDto<>(userService.signIn(userDto));
    }

    @GetMapping("/access")
    public BaseResponseDto<TokenDto> accessRequest(@RequestBody RefreshTokenReqDto refreshTokenReqDto, HttpServletRequest request) {
        TokenDto tokenDto = userService.accessRequest(refreshTokenReqDto.getRefreshToken(), request);
        return new BaseResponseDto<>(tokenDto);
    }
}
