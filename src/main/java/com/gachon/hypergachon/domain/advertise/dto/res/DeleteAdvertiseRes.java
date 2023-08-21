package com.gachon.hypergachon.domain.advertise.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteAdvertiseRes {
    Boolean isSuccess;

    public static DeleteAdvertiseRes of(Boolean isSuccess){
        return DeleteAdvertiseRes.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
