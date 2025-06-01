package com.alex.sortify;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TrashType {
    PLASTIC("plastic"),
    PAPER("paper"),
    GLASS("glass"),
    METAL("metal"),
    ORGANIC("organic");

    private final String typeName;

    TrashType(String typeName) {
        this.typeName = typeName;
    }

    @JsonValue
    public String getTypeName() {
        return typeName;
    }

    @JsonCreator
    public static TrashType fromTypeName(String typeName) {
        for (TrashType type : values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown trash type: " + typeName);
    }
}
