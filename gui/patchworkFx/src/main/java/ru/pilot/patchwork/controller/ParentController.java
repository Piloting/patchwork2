package ru.pilot.patchwork.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;
import ru.pilot.patchwork.MainFx;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.Paint;

public class ParentController {
    
    

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
    
    @SneakyThrows
    public static void openForm(String fileName, String title) {
        // New window (Stage)
        URL form = Thread.currentThread().getContextClassLoader().getResource("form/"+fileName);
        Parent root = FXMLLoader.load(form);

        Stage newWindow = new Stage();
        newWindow.setTitle(title);
        newWindow.setScene(new Scene(root));

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        Stage primaryStage = MainFx.getPrimaryStage();
        newWindow.initOwner(primaryStage);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX());
        newWindow.setY(primaryStage.getY());

        newWindow.show();
    }

    public static int getInt(String text){
        if (NumberUtils.isCreatable(text)) {
            return NumberUtils.createInteger(text);
        }
        return 0;
    }
}
