package com.gachon.hypergachon.global.building;


import com.gachon.hypergachon.global.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.gachon.hypergachon.global.response.ErrorMessage.ENUM_NOT_FOUND;
import static com.gachon.hypergachon.global.response.ErrorMessage.NOT_IN_BUILDING;

@Getter
@AllArgsConstructor
public enum Building {

    NO1_DORMITORY("제1학생생활관",37.45604961141133, 37.45651267479754, 127.13479451181763, 127.13563172068275),
    NO2_DORMITORY("제2학생생활관",37.45569485533425, 37.456441776331616, 127.13378233717839, 127.13459742379997),
    NO3_DORMITORY("제3학생생활관",37.45522283859151,37.45611855397124,127.13288860587926,127.13359093599679),
    PLAYGROUND("종합운동장",37.4544097916902,37.45583695598506,127.13477461576566,127.1355965935118),
    AI_BUILDING("AI공학관",37.454979082034576,37.455410447340746,127.13317072919332,127.13430736853014),
    STUDENT_CENTER("학생회관",37.45258622131423,37.453558684208474,127.13391240607615,127.13446793573594),
    CENTER_LIBRARY("중앙도서관",37.45198848281547,37.45296077977726,127.13263989675134,127.13334798042581),
    LIFELONG_EDU_CENTER("평생교육원",37.45238823861284,37.45288316093551,127.12965693211783,127.13023418111611),
    ARTS_SPORTS_COLLEGE("예술체육대학",37.45198849239433,37.4526184235563,127.12855432418972,127.12926741675187),
    EDU_GRADUATE("교육대학원",37.45150807536928,37.45222773827318,127.13117548481846,127.13220520154556),
    VANE_GARDEN("바람개비동산",37.45159009165806,37.45232817099039,127.13033930503423,127.13101869212356),
    GLOBAL_CENTER("글로벌센터",37.451629846909654,37.45217435658174,127.12692627013416,127.12748662685871),
    ARTS_MUSIC_HALL("예음홀",37.45140221908925,37.45199793313281,127.12911840256812,127.13021851917206),
    NO1_ENGINEERING_COLLEGE("공과대학1",37.45130929430384,37.45186227704742,127.12756992086369,127.12862191840651),
    SEMICONDUCTOR_COLLEGE("반도체대학",37.450573408027225,37.45152337290548,127.12692731049177,127.12748834744346),
    STARDUM_PLAZA("스타덤광장",37.4505410631071,37.450954609617426,127.12767880591119,127.1285271263252),
    ELECTRONIC_LIBRARY("전자정보도서관",37.45032846199009,37.450841453714155,127.1284751983941,127.12901289936279),
    GACHON_BUILDING("가천관",37.44999411385015,37.45079954461877,127.12936743894888,127.13025599716566),
    ORIENTAL_MEDICINE_COLLEGE("한의과대학",37.44993227468773,37.45019263912395,127.12823719114948,127.12909089525193),
    BIO_NANO_LAB("바이오나노연구원",37.44947534506807,37.450211618904255,127.12792844952227,127.12820941436894),
    COOPERATION_CENTER("산학협력관1",37.44904140852292,37.449869657694734,127.12926126150545,127.12988426383622),
    NO2_ENGINEERING_COLLEGE("공과대학2",37.44900666602773,37.4494335091548,127.12806891733716,127.12911501877623),
    VISION_TOWER("비전타워",37.44908215798883,37.450218826308124,127.12699542392674,127.12779409815198);

    private final String name;
    private final Double lowLatitude;
    private final Double topLatitude;
    private final Double leftLongitude;
    private final Double rightLongitude;

    public static Building getBuilding(Double latitude, Double longitude) {
        return Arrays.stream(Building.values())
                .filter(v -> v.isInside(latitude, longitude))
                .findAny()
                .orElseThrow(() -> new BusinessException(NOT_IN_BUILDING));
    }

    public static Building getClosestBuilding(Double latitude, Double longitude) {
        List<Building> buildings = Arrays.stream(Building.values()).toList();
        Building closestBuilding = buildings.get(0);
        Double minDistance = (double) 999;
        for (Building building : buildings) {
            Double distance = building.calculateDistance(latitude, longitude);
            if(minDistance > distance) {
                closestBuilding = building;
                minDistance = distance;
            }
        }
        return closestBuilding;
    }

    private Boolean isInside(Double latitude, Double longitude) {
        return (this.getLeftLongitude() <= longitude
                && this.getRightLongitude() >= longitude
                && this.getLowLatitude() <= latitude
                && this.getTopLatitude() >= latitude);
    }

    private Double getCenterLatitude() {
        return (this.getLowLatitude() + this.getTopLatitude()) / 2;
    }

    private Double getCenterLongitude() {
        return (this.getLeftLongitude() + this.getRightLongitude()) / 2;
    }

    private Double calculateDistance(Double latitude, Double longitude) {
        Double centerLatitude = this.getCenterLatitude();
        Double centerLongitude = this.getCenterLongitude();
        Double distance = calculateDistance(latitude, longitude, centerLatitude, centerLongitude);
        return distance;
    }

    private Double calculateDistance(Double latitude, Double longitude, Double centerLatitude, Double centerLongitude) {
        return Math.sqrt(Math.pow(centerLatitude - latitude, 2) + Math.pow(centerLongitude - longitude, 2));
    }

    public static Building ofCode(String dbData) {
        return Arrays.stream(Building.values())
                .filter(v -> v.getName().equals(dbData))
                .findAny()
                .orElseThrow(() -> new BusinessException(ENUM_NOT_FOUND));
    }
}
