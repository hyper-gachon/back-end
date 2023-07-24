package com.gachon.hypergachon.domain.user.dto.request;

import lombok.Getter;

@Getter
public class EmailCheckDto {
    String email;
    String code;
}
