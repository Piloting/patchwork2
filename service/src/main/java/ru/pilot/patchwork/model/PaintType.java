package ru.pilot.patchwork.model;

import lombok.Getter;

public enum PaintType {
    RANDOM_COLOR("Случайные цвета"),
    PICTURE_COLOR("Цвета по картинке");
    
    @Getter
    private final String name;

    PaintType(String name) {
        this.name = name;
    }
}
