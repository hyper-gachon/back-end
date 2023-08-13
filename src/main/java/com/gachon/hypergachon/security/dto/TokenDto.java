package com.gachon.hypergachon.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDto {
    String accessToken;
    String refreshToken;
}
