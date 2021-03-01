package ru.pilot.patchwork.service.paint.strategy;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;

public abstract class PaintStrategy {
    
    public void fill(IBlock block, ModelConfig modelConfig){
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            fill(polygonBlock, modelConfig);
        }
    }
    
    abstract void fill(PolygonBlock polygonBlock, ModelConfig modelConfig);
}
