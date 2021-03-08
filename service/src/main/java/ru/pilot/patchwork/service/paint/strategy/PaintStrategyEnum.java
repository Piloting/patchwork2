package ru.pilot.patchwork.service.paint.strategy;

import lombok.Getter;
import lombok.SneakyThrows;
import ru.pilot.patchwork.model.PaintType;
import ru.pilot.patchwork.model.StructureType;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategy;
import ru.pilot.patchwork.service.paint.strategy.PictureColor;
import ru.pilot.patchwork.service.paint.strategy.RandomColor;

public enum PaintStrategyEnum {
    RANDOM_COLOR(PaintType.RANDOM_COLOR, RandomColor.class),
    PICTURE_COLOR(PaintType.PICTURE_COLOR, PictureColor.class);

    @Getter
    private final PaintType type;
    @Getter
    private final Class<? extends PaintStrategy> clazz;

    PaintStrategyEnum(PaintType type, Class<? extends PaintStrategy> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    @SneakyThrows
    public static PaintStrategy getImplByType(PaintType type){
        for (PaintStrategyEnum value : values()) {
            if (value.getType().equals(type)){
                return value.getClazz().newInstance();
            }
        }
        throw new RuntimeException("Not implemented");
    }
    
}
