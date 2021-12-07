package net.mwforrest7.vineyard.enums;

import net.minecraft.util.StringIdentifiable;

public enum VineType implements StringIdentifiable
{
    RED_GRAPE("red_grape"),
    WHITE_GRAPE("white_grape");

    private final String name;

    private VineType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}