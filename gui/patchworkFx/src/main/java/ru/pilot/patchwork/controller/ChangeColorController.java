package ru.pilot.patchwork.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import ru.pilot.patchwork.MainFx;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.ImageFill;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;
import ru.pilot.patchwork.utils.FileUtils;

public class ChangeColorController extends ParentController {
    
    @FXML private FlowPane simplePane;
    @FXML private BorderPane mainPane;
    @FXML private ScrollPane scrollPane;
    
    private FileChooser fileChooser;
    private static PaintSet paintSet = null;

    public static PaintSet getPaintSet() {
        return paintSet;
    }

    public static void setPaintSet(PaintSet paintSet) {
        ChangeColorController.paintSet = paintSet;
    }
    
    @FXML
    private void initialize() {
        mainPane.widthProperty().addListener(p -> simplePane.setPrefWidth(mainPane.getWidth()-30));
        mainPane.heightProperty().addListener(p -> scrollPane.setPrefHeight(mainPane.getHeight()));
        fileChooser = FileUtils.getPictFileChooser();
        layout();
    }
    
    private void layout() {
        if (paintSet == null){
            simplePane.getChildren().clear();
            return;
        }
        List<Rectangle> rectList = new ArrayList<>();
        for (Paint paint : paintSet.getPaints()) {
            Rectangle rectangle = createViewColorFill(paint, rectSize);
            rectangle.setId(paint.getId()+"");
            ContextMenu cm = createContextMenu(paintSet, paint.getId());
            rectangle.setOnContextMenuRequested(event -> cm.show(rectangle, event.getScreenX(), event.getScreenY()));
            if (paint instanceof ImageFill){
                rectangle.setOnScroll((ScrollEvent event) -> {
                    event.consume();
                    if (event.getDeltaY() == 0) {
                        return;
                    }
                    Rectangle rect = (Rectangle) event.getSource();
                    javafx.scene.paint.Paint rectFill = rect.getFill();
                    double scaleFactor = (event.getDeltaY() > 0) ? 1.1 : 1 / 1.1;
                    ImagePattern fill = (ImagePattern) rectFill;
                    ImagePattern imagePattern = getImagePattern(fill.getImage(), fill.getWidth() * scaleFactor);
                    rect.setFill(imagePattern);
                });
            }
            rectList.add(rectangle);
        }

        simplePane.getChildren().clear();
        simplePane.getChildren().addAll(rectList);
    }


    private ContextMenu createContextMenu(PaintSet paintSet, Long id) {
        MenuItem menuItem = new MenuItem("Удалить");
        menuItem.setOnAction(event -> {
            paintSet.getPaints().removeIf(p -> p.getId().equals(id));
            layout();
        });
        return new ContextMenu(menuItem);
    }


    @FXML
    void cancelButtonPress(ActionEvent event) {
        paintSet = null;
        close(event);
    }

    @FXML
    void okButtonPress(ActionEvent event) {
        // сохранить в БД
        new LibraryController().savePaintSet(paintSet);
        close(event);
    }

    @FXML
    void addColorButtonPress(ActionEvent event) {
        ColorPicker source = (ColorPicker) event.getSource();
        Color color = source.getValue();
        paintSet.getPaints().add(new ColorFill(
                (int)(color.getRed()*255),
                (int)(color.getGreen()*255),
                (int)(color.getBlue()*255)));
        
        layout();
    }

    @FXML
    void addImageButtonPress(ActionEvent event) throws IOException {
        List<File> files = fileChooser.showOpenMultipleDialog(MainFx.getPrimaryStage());
        for (File file : files) {
            Image image = FileUtils.getImage(file);
            if (image == null){
                return;
            }
            ImageFill newPaint = new ImageFill(file.getAbsolutePath(), Files.readAllBytes(file.toPath()));
            paintSet.getPaints().add(newPaint);
            
            layout();
        }
    }
}
