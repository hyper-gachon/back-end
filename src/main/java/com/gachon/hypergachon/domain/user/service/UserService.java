package com.gachon.hypergachon.domain.user.service;

import com.gachon.hypergachon.domain.user.dto.request.LoginReqDto;
import com.gachon.hypergachon.domain.user.dto.response.LoginResDto;
import com.gachon.hypergachon.domain.user.entity.User;
import com.gachon.hypergachon.domain.user.repository.UserRepository;
import com.gachon.hypergachon.exception.BusinessException;
import com.gachon.hypergachon.domain.user.dto.request.UserDto;
import com.gachon.hypergachon.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gachon.hypergachon.response.ErrorMessage.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TokenProvider tokenProvider;

    // 유저 회원가입
    public String signIn(UserDto userDto){
        if(userRepository.existsByUserId(userDto.getUserId()))
            throw new BusinessException(ALREADY_SIGNUPED_EMAIL_USER);

        String encodedPw = passwordEncoder.encode(userDto.getPassword());

        User user = User.builder()
                .userId(userDto.getUserId())
                .password(encodedPw)
                .name(userDto.getName())
                .build();

        User signedUser = userRepository.save(user);
        return "success";
    }

    // 유저 로그인
    public LoginResDto login(LoginReqDto loginReqDto){
        User findUser = userRepository.findUserByUserId(loginReqDto.getUserId());
        if(findUser== null) throw new BusinessException(USER_NOT_FOUND);
        if(!passwordEncoder.matches(loginReqDto.getPassword(), findUser.getPassword()))
            throw new BusinessException(WRONG_PASSWORD);

        String token = tokenProvider.createToken(String.format("%s:%s", findUser.getId(), loginReqDto.getUserId()));
        return new LoginResDto(findUser.getId(), findUser.getName(), token);
    }
}
