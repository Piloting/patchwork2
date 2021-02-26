package ru.pilot.patchwork.model;

import lombok.Getter;

public enum StructureStrategyType {
    ChessStructure("Шахматка"),
    LineStructure("Линии"),
    RandomStructure("Случайно"),
    WindowStructure("4 квадрата");
    
    @Getter
    private final String name;

    StructureStrategyType(String name) {
        this.name = name;
    }
}
