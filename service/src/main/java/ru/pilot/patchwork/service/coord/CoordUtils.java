package ru.pilot.patchwork.service.coord;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.pilot.patchwork.service.block.PolygonBlock;

public class CoordUtils {
    
    /** Перевод координат вида [0,0, 0,50, 50,0] в список 2d точек */
    public static List<Point> pointsToList(double[] points){
        if (points == null || points.length == 0){
            return Collections.emptyList();
        }
        
        if (!isEven(points.length)){
            throw new InvalidParameterException("UnEven points count!");
        }
        
        List<Point> pointList = new ArrayList<>(points.length/2);
        for (int i = 0; i < points.length; i+=2) {
            pointList.add(new Point(points[i], points[i+1]));
        }
        return pointList;
    }

    /** центральная точка относительно всех полигонов */ 
    public static Point getCenter(List<PolygonBlock> polygonBlocks){
        Point min = new Point();
        Point max = new Point();
        getMinMaxCoord(polygonBlocks, min, max);

        double with = max.getX()-min.getX();
        double height = max.getY()-min.getY();

        return new Point(min.getX()+with/2, min.getY()+height/2);
    }

    private static void getMinMaxCoord(List<PolygonBlock> polygonBlocks, Point min, Point max) {
        double maxX = Double.NEGATIVE_INFINITY;
        double minX = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;

        // найдем крайние точки
        for (PolygonBlock polygon : polygonBlocks) {
            for (Point Point : CoordUtils.pointsToList(polygon.getPoints())) {
                maxX = Math.max(maxX, Point.getX());
                minX = Math.min(minX, Point.getX());
                maxY = Math.max(maxY, Point.getY());
                minY = Math.min(minY, Point.getY());
            }
        }

        min.setX(minX);
        min.setY(minY);
        max.setX(maxX);
        max.setY(maxY);
    }

    /** Возвращает размер Point(with, height) */
    public static Point getSize(List<PolygonBlock> polygonBlocks){
        Point min = new Point();
        Point max = new Point();
        getMinMaxCoord(polygonBlocks, min, max);


        double with = max.getX()-min.getX();
        double height = max.getY()-min.getY();
        
        return new Point(with, height);
    }
    
    public static Point getMin(List<PolygonBlock> polygonBlocks){
        Point min = new Point();
        Point max = new Point();
        getMinMaxCoord(polygonBlocks, min, max);
        return min;
    }
    public static Point getMax(List<PolygonBlock> polygonBlocks){
        Point min = new Point();
        Point max = new Point();
        getMinMaxCoord(polygonBlocks, min, max);
        return max;
    }

    /** четное? */
    public static boolean isEven( int even ) {
        return even % 2 == 0;
    }

}
