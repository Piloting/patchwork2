package ru.pilot.patchwork.ext.javafx;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.PaintSet;

public class PictureUtilsFx {
    
    public static PaintSet getColorSetByPicture(int count, int picture){
        return null;
    }

    public static WritableImage getFitImage(String imageName, double blockWidth, double blockHeight) {
        Image image = new Image(imageName);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(blockWidth);
        imageView.setFitHeight(blockHeight);
        return imageView.snapshot(new SnapshotParameters(), null);
    }

    public static ColorFill getColor(PixelReader pixelReader, int x, int y){
        Color color = pixelReader.getColor(x, y);
        return new ColorFill((int)color.getRed()*255, (int)color.getGreen()*255, (int)color.getBlue()*255);
    }
}
