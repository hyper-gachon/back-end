package com.gachon.hypergachon.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResDto {
    Long id;
    String name;
    String token;
}
