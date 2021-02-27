package ru.pilot.patchwork.service.paint;

import lombok.Getter;
import ru.pilot.patchwork.service.block.BlockIdGenerator;

@Getter
public class ImageFill implements Paint {

    private final Long id = BlockIdGenerator.getId();
    private final String imageName;
    
    /** rectangle - point and size */
    private double x = 0;
    private double y = 0;
    private double width = 100;

    public ImageFill(String imageName) {
        this.imageName = imageName;
    }
}
