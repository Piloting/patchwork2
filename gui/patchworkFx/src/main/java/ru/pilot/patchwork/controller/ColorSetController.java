package ru.pilot.patchwork.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;


public class ColorSetController extends ParentController {

    @FXML private BorderPane mainPane;
    @FXML private ScrollPane vertScrollPane;
    @FXML private VBox vBox;

    private static List<PaintSet> paintSetList = null;
    private static List<PaintSet> deletedPaintSetList = null;
    private static final Map<Long, PaintSet> selectedPaintMap = new HashMap<>();
    private final LibraryController libraryController = new LibraryController();
    
    @FXML
    private void initialize() {
        mainPane.heightProperty().addListener(p -> vertScrollPane.setPrefHeight(mainPane.getHeight()));
        mainPane.widthProperty().addListener(p -> vBox.setPrefWidth(mainPane.getWidth()));
        layout();
    }

    private void layout() {
        paintSetList = libraryController.getPaintSet();
        
        vBox.getChildren().clear();

        if (paintSetList == null){
            return;
        }
        
        for (PaintSet paintSet : paintSetList) {
            HBox newPane = createNewPane(paintSet.getId());
            
            ContextMenu cm = createContextMenu(paintSet);
            newPane.setOnContextMenuRequested(event -> cm.show(newPane, event.getScreenX(), event.getScreenY()));
            
            ObservableList<Node> children = newPane.getChildren();
            for (Paint paint : paintSet.getPaints()) {
                Rectangle rectangle = createViewColorFill(paint, rectSize);
                rectangle.setId(paint.getId()+"");
                children.add(rectangle);
            }
        }
    }
    
    private ContextMenu createContextMenu(PaintSet paintSet) {
        return new ContextMenu(
                deleteMenuItem(paintSet),
                updateMenuItem(paintSet)
        );
    }
    
    private MenuItem deleteMenuItem(PaintSet paintSet) {
        MenuItem menuItem = new MenuItem("Удалить");
        menuItem.setOnAction(event -> {
            paintSetList.removeIf(p -> p.getId().equals(paintSet.getId()));
            deletedPaintSetList.add(paintSet);
            layout();
        });
        return menuItem;
    }
    
    private MenuItem updateMenuItem(PaintSet paintSet) {
        MenuItem menuItem = new MenuItem("Изменить");
        menuItem.setOnAction(event -> {
            ChangeColorController.setPaintSet(paintSet);
            openForm("changeColor.fxml", "Изменение цветов", true);
            layout();
        });
        return menuItem;
    }

    private HBox createNewPane(Long id){
        HBox newHBox = new HBox();
        newHBox.setPrefHeight(rectSize);
        newHBox.setSpacing(10);
        newHBox.setId(id+"");
        newHBox.setPadding(new Insets(10,10,10,10));
        
        ScrollPane newGorScrollPane = new ScrollPane(newHBox);
        newGorScrollPane.setPrefHeight(rectSize+60);
        newGorScrollPane.setPrefWidth(mainPane.getWidth());
        newGorScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainPane.widthProperty().addListener(p -> newGorScrollPane.setPrefWidth(mainPane.getWidth()));

        vBox.getChildren().add(newGorScrollPane);

        if (selectedPaintMap.containsKey(id)){
            newHBox.setBackground(selectBackground());
        }

        newHBox.setOnMouseClicked(event -> {
            HBox source = (HBox) event.getSource();
            if (selectedPaintMap.containsKey(id)){
                selectedPaintMap.keySet().remove(id);
                source.setBackground(null);
            } else {
                PaintSet paintSet = paintSetList.stream().filter(ps -> ps.getId().equals(id)).findFirst().orElse(null);
                selectedPaintMap.put(id, paintSet);
                source.setBackground(selectBackground());
            }
        });
        
        return newHBox;
    }

    private Background selectBackground() {
        return new Background(new BackgroundFill(Color.GREEN, null, null));
    }

    @FXML
    void cancelButtonPress(ActionEvent event) {
        paintSetList = null;
        deletedPaintSetList = null;
        selectedPaintMap.clear();
        close(event);
    }

    @FXML
    void okButtonPress(ActionEvent event) {
        // сохранить в БД удаленные и добавленные
        if (paintSetList != null){
            for (PaintSet paintSet : paintSetList) {
                libraryController.savePaintSet(paintSet);
            }
        }
        if (deletedPaintSetList != null) {
            for (PaintSet paintSet : deletedPaintSetList) {
                libraryController.removePaintSet(paintSet.getId());
            }
        }

        close(event);
    }

    @FXML
    void addButtonPress(ActionEvent event) {
        List<PaintSet> paintSetList = libraryController.getPaintSet();
        PaintSet paintSet = paintSetList.iterator().next();
        ChangeColorController.setPaintSet(paintSet);
        openForm("changeColor.fxml", "Выбор цветов", true);
        layout();
    }
    
    public static Map<Long, PaintSet> getSelectedPaintSetIds(){
        return selectedPaintMap;
    }
    public static void setSelectedPaintSetIds(Map<Long, PaintSet> selectedPaintMapLocal){
        selectedPaintMap.clear();
        selectedPaintMap.putAll(selectedPaintMapLocal);
    }

}

