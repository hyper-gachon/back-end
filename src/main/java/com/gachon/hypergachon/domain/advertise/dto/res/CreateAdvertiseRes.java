package com.gachon.hypergachon.domain.advertise.dto.res;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateAdvertiseRes {
    Long postId;

    public static CreateAdvertiseRes of(Long postId) {
        return CreateAdvertiseRes.builder()
                .postId(postId)
                .build();
    }
}
