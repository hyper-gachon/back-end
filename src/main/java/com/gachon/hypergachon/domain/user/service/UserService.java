package com.gachon.hypergachon.domain.user.service;

import com.gachon.hypergachon.domain.user.dto.request.LoginReqDto;
import com.gachon.hypergachon.domain.user.dto.response.LoginResDto;
import com.gachon.hypergachon.domain.user.dto.response.SignInResDto;
import com.gachon.hypergachon.domain.user.entity.User;
import com.gachon.hypergachon.domain.user.repository.UserRepository;
import com.gachon.hypergachon.global.exception.BusinessException;
import com.gachon.hypergachon.domain.user.dto.request.UserDto;
import com.gachon.hypergachon.global.security.AuthConstants;
import com.gachon.hypergachon.global.security.TokenProvider;
import com.gachon.hypergachon.global.security.dto.TokenDto;
import com.gachon.hypergachon.global.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gachon.hypergachon.global.response.ErrorMessage.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    // 유저 회원가입
    public SignInResDto signIn(UserDto userDto){
        if(userRepository.existsByUserId(userDto.getUserId()))
            throw new BusinessException(ALREADY_SIGNUPED_EMAIL_USER);

        String encodedPw = passwordEncoder.encode(userDto.getPassword());

        User user = User.builder()
                .userId(userDto.getUserId())
                .password(encodedPw)
                .name(userDto.getName())
                .build();

        userRepository.save(user);

        return SignInResDto.of(true);
    }

    // 유저 로그인
    public LoginResDto login(LoginReqDto loginReqDto, HttpServletResponse httpServletResponse){
        User findUser = userRepository.findUserByUserId(loginReqDto.getUserId());
        if(findUser== null) throw new BusinessException(USER_NOT_FOUND);
        if(!passwordEncoder.matches(loginReqDto.getPassword(), findUser.getPassword()))
            throw new BusinessException(WRONG_PASSWORD);

        // refreshToken 생성
        String refreshToken = tokenProvider.createRefreshToken();
        // accessToken 생성
        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", findUser.getId(), loginReqDto.getUserId()));
        if (redisUtil.getData(findUser.getUserId()) != null) {
            String originalRefreshToken = redisUtil.getData(findUser.getUserId());
            long refreshExpireTime = TokenProvider.getExpDateFromToken(originalRefreshToken) * 1000;
            long diffDays = (refreshExpireTime - System.currentTimeMillis()) / 1000 / (24 * 3600);
            System.out.println("diff day : " + diffDays);
            if (diffDays <= 2) {
                redisUtil.setDataExpire(findUser.getUserId(), refreshToken, 60 * 60 * 24 * 5L);
            } else {
                refreshToken = originalRefreshToken;
            }
        } else {
            redisUtil.setDataExpire(findUser.getUserId(), refreshToken, 60 * 60 * 24 * 5L);
        }



        return LoginResDto.of(findUser.getId(), findUser.getName(), accessToken, refreshToken);
    }

    // 첫번째 access token에서 exp가 만료되었을 때 부르는 api로
    // header에 담긴 token을 까서 redis에서 refresh토큰을 찾고
    public TokenDto accessRequest(String refreshToken, HttpServletRequest request){

        // [STEP1] Client에서 API를 요청할때 Header를 확인합니다.
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        // [STEP2] Header 내에 토큰을 추출합니다.
        String accessToken = header.split(" ")[1];

        String[] split = Optional.ofNullable(accessToken)
                .filter(subject -> subject.length() >= 10)
                .map(tokenProvider::getTokenSubject)
                .orElse("anonymous:anonymous")
                .split(":");

        String userId = split[1];

        if (redisUtil.getData(userId) != null) {
            long refreshExpireTime = TokenProvider.getExpDateFromToken(refreshToken) * 1000;
            long diffDays = (refreshExpireTime - System.currentTimeMillis()) / 1000 / (24 * 3600);


            if (diffDays <= 2) {
                refreshToken = tokenProvider.createRefreshToken();
                redisUtil.setDataExpire(userId, refreshToken, 60 * 60 * 24 * 5L);
            }
            accessToken = tokenProvider.createAccessToken(String.format("%s:%s", split[0], split[1]));
        } else {
            throw new BusinessException(INVAILID_JWT_REFRESH_TOKEN);
        }
        return TokenDto.of(accessToken, refreshToken);
    }
}
