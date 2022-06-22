package net.mwforrest7.vineyard.enums;

import net.minecraft.util.StringIdentifiable;

public enum VineType implements StringIdentifiable
{
    // Different grape types - used to make different types of vine trunk blocks
    RED_GRAPE("red_grape"),
    GREEN_GRAPE("green_grape");

    private final String name;

    VineType(String name) {
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