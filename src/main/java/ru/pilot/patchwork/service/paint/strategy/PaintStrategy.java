package ru.pilot.patchwork.service.paint.strategy;

import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;

public interface PaintStrategy {
    
    default void fill(IBlock block){
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            fill(polygonBlock);
        }
    }
    
    void fill(PolygonBlock polygonBlock);
    
}
