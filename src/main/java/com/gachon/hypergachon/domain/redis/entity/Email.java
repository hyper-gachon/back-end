package com.gachon.hypergachon.domain.redis.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value="email")
public class Email {
    @Id
    private Long id;

    private String key;

    public Email(Long id, String key) {
        this.id = id;
        this.key = key;
    }
}
