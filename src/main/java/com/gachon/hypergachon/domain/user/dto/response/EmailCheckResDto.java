package com.gachon.hypergachon.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailCheckResDto {
    Boolean isSuccess;

    public static EmailCheckResDto of(Boolean isSuccess){
        return EmailCheckResDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
