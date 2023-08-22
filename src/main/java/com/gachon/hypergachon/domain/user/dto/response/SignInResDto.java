package com.gachon.hypergachon.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignInResDto {
    Boolean isSuccess;

    public static SignInResDto of(Boolean isSuccess){
        return SignInResDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
