package com.gachon.hypergachon.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailSendRes {
    Boolean isSuccess;

    public static EmailSendRes of(Boolean isSuccess){
        return EmailSendRes.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
