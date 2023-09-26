package com.gachon.hypergachon.domain.advertise.dto.res;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateAdvertiseResDto {
    Long postId;

    public static CreateAdvertiseResDto of(Long postId) {
        return CreateAdvertiseResDto.builder()
                .postId(postId)
                .build();
    }
}
