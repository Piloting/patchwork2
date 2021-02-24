package ru.pilot.patchwork.service.paint;

import lombok.Getter;

@Getter
public class ImageFill implements Paint {
    private String imageName;
    
    /** rectangle - point and size */
    private double x = 0;
    private double y = 0;
    private double width = 10;
    
}
