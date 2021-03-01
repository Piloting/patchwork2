package ru.pilot.patchwork.service.paint.strategy;

import lombok.Getter;
import ru.pilot.patchwork.model.PaintType;
import ru.pilot.patchwork.model.StructureType;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategy;
import ru.pilot.patchwork.service.paint.strategy.PictureColor;
import ru.pilot.patchwork.service.paint.strategy.RandomColor;

public enum PaintStrategyEnum {
    RANDOM_COLOR(PaintType.RANDOM_COLOR, new RandomColor()),
    PICTURE_COLOR(PaintType.PICTURE_COLOR, new PictureColor());

    @Getter
    private final PaintType type;
    @Getter
    private final PaintStrategy impl;

    PaintStrategyEnum(PaintType type, PaintStrategy impl) {
        this.type = type;
        this.impl = impl;
    }

    public static PaintStrategy getImplByType(PaintType type){
        for (PaintStrategyEnum value : values()) {
            if (value.getType().equals(type)){
                return value.getImpl();
            }
        }
        throw new RuntimeException("Not implemented");
    }
    
}
