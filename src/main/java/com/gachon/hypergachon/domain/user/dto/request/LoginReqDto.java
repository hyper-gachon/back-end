package com.gachon.hypergachon.domain.user.dto.request;

import lombok.Getter;

@Getter
public class LoginReqDto {
    private String userId;
    private String password;
}
