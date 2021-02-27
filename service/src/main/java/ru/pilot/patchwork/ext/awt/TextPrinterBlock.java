package ru.pilot.patchwork.ext.awt;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;

public class TextPrinterBlock {
    
    private static final Logger logger = LoggerFactory.getLogger(TextPrinterBlock.class);
    
    public static String print(IBlock block){
        if (block == null){
            return "";
        }
        return print(block.getPolygonBlocks());
    }
    
    public static String print(List<PolygonBlock> polygonBlocks){
        int height = 100;
        int width = 100;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Courier New", Font.PLAIN, 10));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        for (PolygonBlock polygonBlock : polygonBlocks) {
            double[] points = polygonBlock.getPoints();
            logger.trace(Arrays.toString(points));
            
            int size = points.length;
            int[] pointsX = new int[size/2+1]; 
            int[] pointsY = new int[size/2+1];
            int j = 0;
            for (int i = 0; i < size;i+=2) {
                pointsX[j] = (int)points[i];
                pointsY[j] = (int)points[i+1];
                j++;
            }

            Polygon polygon = new Polygon(pointsX, pointsY, size/2);
            graphics.drawPolygon(polygon);
        }


        StringBuilder all = new StringBuilder();
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < height; x++) {
                sb.append(image.getRGB(x, y) == -16777216 ? " " : ".");
            }
            if (sb.toString().trim().isEmpty()) {
                continue;
            }
            all.append(sb).append("\n");
        }
        logger.debug("\n" + all.toString());
        return all.toString();
    }
    
    // цветная консоль
    //logger.debug("\n" + "\u001B[31m" + all.toString() +  "\u001B[0m");
    //logger.debug("\n" + "\u001B[42m" + all.toString() +  "\u001B[0m");
    public static final String ANSI_RESET = "\u001B[0m";
    
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
}
