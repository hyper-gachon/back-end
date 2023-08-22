package com.gachon.hypergachon.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDto {
    private Long userId;
    private String name;
    private String accessToken;
    private String refreshToken;
    public static LoginResDto of(Long userId, String name, String accessToken, String refreshToken){
        return LoginResDto.builder()
                .userId(userId)
                .name(name)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
