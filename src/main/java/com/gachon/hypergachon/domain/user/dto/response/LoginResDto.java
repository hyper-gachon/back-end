package com.gachon.hypergachon.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResDto {
    private Long id;
    private String name;
    private String accessToken;
    private String refreshToken;
}
