package com.gachon.hypergachon.domain.user.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRequestResDto {
    private String refreshToken;
    private String accessToken;
}
