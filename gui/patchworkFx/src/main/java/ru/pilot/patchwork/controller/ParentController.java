package ru.pilot.patchwork.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import ru.pilot.patchwork.MainFx;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.ImageFill;
import ru.pilot.patchwork.service.paint.Paint;

public class ParentController {
    
    protected int rectSize = 200;

    protected Group createViewBlockSet(BlockSet blockSet, int width, int height){
        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        Point size = CoordUtils.getSize(blockSet.getPolygonBlocks());
        manipulator.scale(blockSet, width/size.getX(), height/size.getY());
        Group group = new Group();
        for (Block block : blockSet.getBlocks()) {
            group.getChildren().add(createViewBlock(block));
        }
        return group;
    }
    
    protected Group createViewBlock(Block block){
        Group group = new Group();
        ObservableList<Node> children = group.getChildren();
        group.setId(block.getId()+"");
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            Polygon polygon = new Polygon(polygonBlock.getPoints());
            polygon.setId(polygonBlock.getId()+"");
            polygon.setStroke(Color.BLACK);
            Paint paint = polygonBlock.getPaint();
            if (paint instanceof ColorFill){
                ColorFill colorFill = (ColorFill) paint;
                Color color = Color.rgb(colorFill.getRed(), colorFill.getGreen(), colorFill.getBlue());
                polygon.setFill(color);
            }
            children.add(polygon);
        }
        return group;
    }
    
    protected Rectangle createViewColorFill(Paint paint, int rectSize) {
        return new Rectangle(rectSize, rectSize, createFxPaint(paint, rectSize));
    }

    private javafx.scene.paint.Paint createFxPaint(Paint paint, int width) {
        if (paint instanceof ColorFill) {
            ColorFill colorFill = (ColorFill) paint;
            return Color.rgb(colorFill.getRed(), colorFill.getGreen(), colorFill.getBlue());
        } else if (paint instanceof ImageFill) {
            ImageFill imageFill = (ImageFill) paint;
            String imageName = imageFill.getImageName();
            //Image image = FileUtils.getImage(new File(imageName));
            Image image = new Image(new ByteArrayInputStream(imageFill.getImageBody()));
            
            return getImagePattern(image, width);
        }
        
        throw new RuntimeException("Not implemented paint - " + paint.getClass().getSimpleName());
    }

    public static ImagePattern getImagePattern(Image image, double rectWidth) {
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        ImagePattern imagePattern;
        if (imageWidth == imageHeight){
            imagePattern = new ImagePattern(image, 0, 0, rectWidth, rectWidth, false);
        } else if (imageHeight < imageWidth){
            double h = rectWidth / (imageWidth/imageHeight);
            imagePattern = new ImagePattern(image, 0, 0, rectWidth, h, false);
        } else {
            double w = rectWidth / (imageHeight/imageWidth);
            imagePattern = new ImagePattern(image, 0, 0, w, rectWidth, false);
        }
        return imagePattern;
    }

    @SneakyThrows
    public static void openForm(String fileName, String title, boolean isModal) {
        // New window (Stage)
        URL form = Thread.currentThread().getContextClassLoader().getResource("form/"+fileName);
        Parent root = FXMLLoader.load(form);

        Stage newWindow = new Stage();
        newWindow.setTitle(title);
        newWindow.setScene(new Scene(root));

        // Specifies the modality for new window.
        if (isModal){
            newWindow.initModality(Modality.APPLICATION_MODAL);
        }

        // Specifies the owner Window (parent) for new window
        Stage primaryStage = MainFx.getPrimaryStage();
        newWindow.initOwner(primaryStage);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX());
        newWindow.setY(primaryStage.getY());

        newWindow.showAndWait();
    }

    public static int getInt(String text){
        try{
            return Integer.decode(text);
        } catch (Exception e){
            return 0;
        }
    }
    
    protected void close(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
