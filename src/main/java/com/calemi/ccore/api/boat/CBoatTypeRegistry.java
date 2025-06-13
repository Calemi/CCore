package com.calemi.ccore.api.boat;

import java.util.ArrayList;
import java.util.List;

public class CBoatTypeRegistry {

    private static final List<CBoatType> TYPES = new ArrayList<>();

    public static void register(CBoatType boatType) {
        TYPES.add(boatType);
    }

    public static List<CBoatType> getTypes() {
        return TYPES;
    }

    public static int getIndex(CBoatType type) {

        for (int index = 0; index < TYPES.size(); index++) {

            if (TYPES.get(index).equals(type)) {
                return index;
            }
        }

        return 0;
    }

    public static CBoatType byIndex(int index) {
        return TYPES.get(index);
    }

    public static CBoatType byName(String name) {
        return TYPES.stream().filter(type -> type.getName().equals(name)).findFirst().orElse(null);
    }
}
