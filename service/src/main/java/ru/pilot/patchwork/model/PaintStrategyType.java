package ru.pilot.patchwork.model;

import lombok.Getter;

public enum PaintStrategyType {
    RANDOM_COLOR("Случайные цвета"),
    PICTURE_COLOR("Цвета по картинке");
    
    @Getter
    private final String name;

    PaintStrategyType(String name) {
        this.name = name;
    }
}
