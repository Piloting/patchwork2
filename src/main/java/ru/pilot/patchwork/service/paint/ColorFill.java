package ru.pilot.patchwork.service.paint;

import lombok.Getter;

@Getter
public class ColorFill implements Paint {

    public static final ColorFill BLACK = new ColorFill(0, 0, 0);
    public static final ColorFill RED = new ColorFill(1, 0, 0);
    public static final ColorFill GREEN = new ColorFill(0, 1, 0);
    public static final ColorFill BLUE = new ColorFill(0, 0, 1);
    public static final ColorFill WHITE = new ColorFill(1, 1, 1);
    
    private final int red;
    private final int green;
    private final int blue;

    public ColorFill(int red, int green, int blue) {
        checkRGB(red, green, blue);
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
