package ru.pilot.patchwork.controller;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;


public class ColorSetController extends ParentController {

    @FXML private BorderPane mainPane;
    @FXML private ScrollPane vertScrollPane;
    @FXML private VBox vBox;

    private static List<PaintSet> paintSetList = null;
    private final LibraryController libraryController = new LibraryController();
    
    @FXML
    private void initialize() {
        mainPane.heightProperty().addListener(p -> vertScrollPane.setPrefHeight(mainPane.getHeight()));
        mainPane.widthProperty().addListener(p -> vBox.setPrefWidth(mainPane.getWidth()));
        layout();
    }

    private void layout() {
        List<PaintSet> paintSetList = libraryController.getPaintSet();
        
        if (paintSetList == null){
            vBox.getChildren().clear();
            return;
        }
        
        for (PaintSet paintSet : paintSetList) {
            HBox newPane = createNewPane();
            ObservableList<Node> children = newPane.getChildren();
            for (Paint paint : paintSet.getPaints()) {
                Rectangle rectangle = createViewColorFill(paint, rectSize);
                rectangle.setId(paint.getId()+"");
                //ContextMenu cm = createContextMenu(paintSet, paint.getId());
                //rectangle.setOnContextMenuRequested(event -> cm.show(rectangle, event.getScreenX(), event.getScreenY()));
                children.add(rectangle);
            }
        }
    }
    
    private HBox createNewPane(){
        HBox newHBox = new HBox();
        newHBox.setPrefHeight(rectSize);
        newHBox.setSpacing(10);
        newHBox.setPadding(new Insets(10,10,10,10));
        
        ScrollPane newGorScrollPane = new ScrollPane(newHBox);
        newGorScrollPane.setPrefHeight(rectSize+60);
        newGorScrollPane.setPrefWidth(mainPane.getWidth());
        newGorScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainPane.widthProperty().addListener(p -> newGorScrollPane.setPrefWidth(mainPane.getWidth()));

        vBox.getChildren().add(newGorScrollPane);
        
        return newHBox;
    }

    @FXML
    private Button okButtonPress;

    @FXML
    private Button addButtonPress;

    @FXML
    private Button cancelButtonPress;

    @FXML
    void cancelButtonPress(ActionEvent event) {

    }

    @FXML
    void okButtonPress(ActionEvent event) {

    }

    @FXML
    void addButtonPress(ActionEvent event) {
        List<PaintSet> paintSetList = libraryController.getPaintSet();
        PaintSet paintSet = paintSetList.iterator().next();
        ChangeColorController.setPaintSet(paintSet);
        openForm("changeColor.fxml", "Выбор цветов", true);
    }

}

