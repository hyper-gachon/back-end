package com.gachon.hypergachon.domain.user.dto.request;

import lombok.Getter;

@Getter
public class EmailCheckDto {
    private String email;
    private String code;
}
