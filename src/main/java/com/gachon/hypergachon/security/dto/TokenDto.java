package com.gachon.hypergachon.security.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {
    String accessToken;
    String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken){
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
