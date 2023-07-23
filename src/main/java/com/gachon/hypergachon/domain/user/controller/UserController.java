package com.gachon.hypergachon.domain.user.controller;

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

    @GetMapping("/emails/test")
    public String testEmail(@RequestBody EmailReqDto emailReqDto) throws MessagingException, UnsupportedEncodingException {
        return emailService.sendEmail(emailReqDto.getEmail());
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
