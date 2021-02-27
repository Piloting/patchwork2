package ru.pilot.patchwork.service.paint.strategy;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.PaintStrategyType;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;

public abstract class PaintStrategy {
    
    public void fill(IBlock block, ModelConfig modelConfig){
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            fill(polygonBlock, modelConfig);
        }
    }
    
    abstract void fill(PolygonBlock polygonBlock, ModelConfig modelConfig);
    
    public static PaintStrategy getImplByType(PaintStrategyType type){
        switch (type){
            case RANDOM_COLOR: return new RandomColor();
            case PICTURE_COLOR: return new PictureColor();
            default: throw new RuntimeException("Not implemented");
        }
    }
}
