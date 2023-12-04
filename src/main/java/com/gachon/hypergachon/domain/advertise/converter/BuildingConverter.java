package com.gachon.hypergachon.domain.advertise.converter;

import com.gachon.hypergachon.global.building.Building;
import jakarta.persistence.AttributeConverter;

public class BuildingConverter implements AttributeConverter<Building, String> {


    @Override
    public String convertToDatabaseColumn(Building building) {
        if(building == null) return null;
        return building.getName();
    }

    @Override
    public Building convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        try {
            return Building.ofCode(dbData);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
