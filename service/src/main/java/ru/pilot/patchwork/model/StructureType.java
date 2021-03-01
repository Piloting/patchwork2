package ru.pilot.patchwork.model;

import lombok.Getter;

public enum StructureType {
    CHESS_STRUCTURE("Шахматка"),
    LINE_STRUCTURE("Линии"),
    RANDOM_STRUCTURE("Случайно"),
    WINDOW_STRUCTURE("4 квадрата");
    
    @Getter
    private final String name;

    StructureType(String name) {
        this.name = name;
    }
}
