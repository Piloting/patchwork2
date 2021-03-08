package ru.pilot.patchwork.ext.javafx;


import java.util.Arrays;
import java.util.List;

import javafx.geometry.BoundingBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.MirrorType;
import ru.pilot.patchwork.service.coord.Point;

public class BlockPointManipulatorFx implements BlockPointManipulator {
    
    private static final Logger logger = LoggerFactory.getLogger(BlockPointManipulatorFx.class);
    
    public void mirror(IBlock block, MirrorType mirrorType){
        Point center = block.getCenter();
        Scale scale = new Scale(mirrorType.getScaleX(), mirrorType.getScaleY(), center.getX(), center.getY());
        
        transformThis(block.getPolygonBlocks(), scale);
    }
    
    public void mirror(List<PolygonBlock> polygonBlocks, MirrorType mirrorType){
        Point center = CoordUtils.getCenter(polygonBlocks);
        Scale scale = new Scale(mirrorType.getScaleX(), mirrorType.getScaleY(), center.getX(), center.getY());
        
        transformThis(polygonBlocks, scale);
    }
    
    public void rotation(IBlock block, double angle){
        if (angle >= 360){
            angle = angle % 360;
        }

        // повернуть относительно центра всего блока
        Point center = block.getCenter();
        Rotate rotate = new Rotate(angle, center.getX(), center.getY());

        transformThis(block.getPolygonBlocks(), rotate);
    }
    
    public void rotation(List<PolygonBlock> polygonBlocks, double angle){
        if (angle >= 360){
            angle = angle % 360;
        }

        // повернуть относительно центра всего блока
        Point center = CoordUtils.getCenter(polygonBlocks);
        Rotate rotate = new Rotate(angle, center.getX(), center.getY());

        transformThis(polygonBlocks, rotate);
    }
    
    public void translate(IBlock block, double deltaX, double deltaY){
        if (deltaX == 0 && deltaY == 0){
            return;
        }

        Translate translate = new Translate(deltaX, deltaY);
        
        transformThis(block.getPolygonBlocks(), translate);
    }
    
    public void translate(List<PolygonBlock> polygonBlocks, double deltaX, double deltaY){
        if (deltaX == 0 && deltaY == 0){
            return;
        }

        Translate translate = new Translate(deltaX, deltaY);
        
        transformThis(polygonBlocks, translate);
    }
    
    public void scale(IBlock block, double zoomX, double zoomY){
        if (zoomX == 1 && zoomY == 1){
            return;
        }

        // увеличить относительно центра всего блока  
        Point center = block.getCenter();
        Scale scale = new Scale(zoomX, zoomY, center.getX(), center.getY());
        
        transformThis(block.getPolygonBlocks(), scale);
    }
    
    public void scale(List<PolygonBlock> polygonBlocks, double zoomX, double zoomY){
        if (zoomX == 1 && zoomY == 1){
            return;
        }

        // увеличить относительно центра всего блока  
        Point center = CoordUtils.getCenter(polygonBlocks);
        Scale scale = new Scale(zoomX, zoomY, center.getX(), center.getY());
        
        transformThis(polygonBlocks, scale);
    }
    
    public boolean isIntersect(PolygonBlock polygonBlock1, Point point) {
        return new Polygon(polygonBlock1.getPoints()).intersects(new BoundingBox(point.getX(), point.getY(), 0, 0));
    }

    private void transformThis(List<PolygonBlock> polygonBlocks, Transform transform){
        for (PolygonBlock polygonBlock : polygonBlocks) {
            double[] currentPoints = polygonBlock.getPoints();
            double[] newPoints = new double[currentPoints.length];

            // повернуть относительно центра всего блока
            transform.transform2DPoints(currentPoints, 0, newPoints, 0, currentPoints.length/2);

            polygonBlock.setPoints(newPoints);
            logger.trace(transform.toString() + ": " + Arrays.toString(currentPoints) + " -> " + Arrays.toString(newPoints));
        }
    }
}
