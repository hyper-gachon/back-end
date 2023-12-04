package com.gachon.hypergachon.domain.location.dto.response;

import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAdvertiseRes {

    private Long postId;

    private String title;

    private String content;

    public static GetAdvertiseRes of(Advertise advertise) {
        return GetAdvertiseRes.builder()
                .postId(advertise.getId())
                .title(advertise.getTitle())
                .content(advertise.getContent())
                .build();
    }
}
