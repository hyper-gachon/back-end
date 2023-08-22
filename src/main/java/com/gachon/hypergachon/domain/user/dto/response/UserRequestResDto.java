package com.gachon.hypergachon.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequestResDto {
    private String refreshToken;
    private String accessToken;

    public static UserRequestResDto of(String refreshToken, String accessToken){
        return UserRequestResDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
}
