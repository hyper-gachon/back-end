package com.gachon.hypergachon.domain.advertise.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteAdvertiseResDto {
    Boolean isSuccess;

    public static DeleteAdvertiseResDto of(Boolean isSuccess){
        return DeleteAdvertiseResDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
