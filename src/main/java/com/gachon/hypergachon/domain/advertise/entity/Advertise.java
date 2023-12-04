package com.gachon.hypergachon.domain.advertise.entity;

import com.gachon.hypergachon.domain.advertise.converter.BuildingConverter;
import com.gachon.hypergachon.domain.user.entity.User;
import com.gachon.hypergachon.global.building.Building;
import com.gachon.hypergachon.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Advertise extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    @Convert(converter = BuildingConverter.class)
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @Builder
    public Advertise(
            User user,
            String title,
            String content,
            LocalDate startDate,
            LocalDate endDate,
            Double latitude,
            Double longitude,
            Building building
    ) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.building = building;
    }

    public void update(String title,
                       String content,
                       LocalDate startDate,
                       LocalDate endDate,
                       Double latitude,
                       Double longitude,
                       Building building) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.building = building;
    }
}
