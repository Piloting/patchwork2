package ru.pilot.patchwork.service.paint;

import lombok.Getter;
import ru.pilot.patchwork.service.block.BlockIdGenerator;

@Getter
public class ColorFill implements Paint {
    
    private final Long id = BlockIdGenerator.getId();

    public static final ColorFill BLACK = new ColorFill(0, 0, 0);
    public static final ColorFill RED = new ColorFill(255, 0, 0);
    public static final ColorFill GREEN = new ColorFill(0, 255, 0);
    public static final ColorFill BLUE = new ColorFill(0, 0, 255);
    public static final ColorFill WHITE = new ColorFill(255, 255, 255);
    
    private final int red;
    private final int green;
    private final int blue;

    /** Для такой строки - #3c1d25 */
    public static ColorFill web(String web) {
        web = web.substring(1);
        return new ColorFill(
                Integer.parseInt(web.substring(0, 1), 16),
                Integer.parseInt(web.substring(1, 2), 16), 
                Integer.parseInt(web.substring(2, 3), 16));
    }
    public ColorFill(int red, int green, int blue) {
        
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    private static void checkRGB(int red, int green, int blue) {
        checkRgb(red, "red");
        checkRgb(green, "green");
        checkRgb(blue, "blue");
    }

    private static void checkRgb(int value, String color) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Color.rgb's " + color + " parameter (" + value + ") expects color values 0-255");
        }
    }
}
