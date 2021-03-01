package ru.pilot.patchwork.service.struct.strategy;

import lombok.Getter;
import ru.pilot.patchwork.model.StructureType;

public enum StructureStrategyEnum {
    CHESS_STRUCTURE(StructureType.CHESS_STRUCTURE, new ChessStructure()),
    LINE_STRUCTURE(StructureType.LINE_STRUCTURE, new LineStructure()),
    RANDOM_STRUCTURE(StructureType.RANDOM_STRUCTURE, new RandomStructure()),
    WINDOW_STRUCTURE(StructureType.WINDOW_STRUCTURE, new WindowStructure());

    @Getter
    private final StructureType type;
    @Getter
    private final StructureStrategy impl;

    StructureStrategyEnum(StructureType type, StructureStrategy impl) {
        this.type = type;
        this.impl = impl;
    }

    public static StructureStrategy getImplByType(StructureType type){
        for (StructureStrategyEnum value : values()) {
            if (value.getType().equals(type)){
                return value.getImpl();
            }
        }
        throw new RuntimeException("Not implemented");
    }
}
