package ru.pilot.patchwork.service.coord;


import java.util.List;

import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;

public interface BlockPointManipulator {
    void mirror(IBlock block, MirrorType mirrorType);
    void mirror(List<PolygonBlock> polygonBlocks, MirrorType mirrorType);
    void rotation(IBlock block, double angle);
    void rotation(List<PolygonBlock> polygonBlocks, double angle);
    void translate(IBlock block, double deltaX, double deltaY);
    void translate(List<PolygonBlock> polygonBlocks, double deltaX, double deltaY);
    void scale(IBlock block, double zoomX, double zoomY);
    void scale(List<PolygonBlock> polygonBlocks, double zoomX, double zoomY);
    
    boolean isIntersect(PolygonBlock polygonBlock1, PolygonBlock polygonBlock2);

}
