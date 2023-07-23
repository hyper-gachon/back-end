package com.gachon.hypergachon.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Advertise> advertises = new ArrayList<>();

    @Builder
    public User(String userId, String password, String name){
        this.userId = userId;
        this.password = password;
        this.name = name;
    }
}
