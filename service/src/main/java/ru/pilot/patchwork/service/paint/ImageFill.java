package ru.pilot.patchwork.service.paint;

import lombok.Getter;
import lombok.Setter;
import ru.pilot.patchwork.service.block.BlockIdGenerator;

@Getter
public class ImageFill implements Paint {

    private final Long id = BlockIdGenerator.getId();
    private final String imageName;
    private final byte[] imageBody;
    
    /** rectangle - point and size */
    @Setter
    private double width = 100;

    public ImageFill(String imageName, byte[] imageBody) {
        this.imageName = imageName;
        this.imageBody = imageBody;
    }
}
