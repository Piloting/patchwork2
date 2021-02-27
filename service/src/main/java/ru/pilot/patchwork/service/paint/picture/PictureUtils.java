package ru.pilot.patchwork.service.paint.picture;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import ru.pilot.patchwork.ext.javafx.PictureUtilsFx;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.PaintSet;

public class PictureUtils {
    
    public static PaintSet getColorSetByPicture(int count, int picture){
        return PictureUtilsFx.getColorSetByPicture(count, picture);
    }

    public static WritableImage getFitImage(String imageName, double blockWidth, double blockHeight) {
        return PictureUtilsFx.getFitImage(imageName, blockWidth, blockHeight);
    }

    public static ColorFill getColor(PixelReader pixelReader, int x, int y){
        return PictureUtilsFx.getColor(pixelReader, x, y);
    }
}
