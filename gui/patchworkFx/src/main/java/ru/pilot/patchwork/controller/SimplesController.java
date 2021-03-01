package ru.pilot.patchwork.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelService;
import ru.pilot.patchwork.model.PaintType;
import ru.pilot.patchwork.model.StructureType;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategyEnum;
import ru.pilot.patchwork.service.struct.strategy.StructureStrategy;
import ru.pilot.patchwork.service.struct.strategy.StructureStrategyEnum;

public class SimplesController extends ParentController {

    private PatchworkModelController modelController;
    
    private int simpleBlockSize = 300;
    private final List<BlockSet> currentSimpleList = new ArrayList<>();
    private final List<StructureType> currentStructureTypeList = new ArrayList<>();
    
    @FXML private TextField sizeXTextField;
    @FXML private Pane mainPane;
    @FXML private ScrollPane scrollPane;
    @FXML private FlowPane simplePane;
    @FXML private CheckBox chessCheckBox;
    @FXML private CheckBox windowCheckBox;
    @FXML private CheckBox lineCheckBox;
    @FXML private CheckBox randomCheckBox;
    @FXML private TextField sizeYTextField;
    @FXML private Button colorSetButton;
    @FXML private Button blockButton;
    @FXML private Button imageSetButton;

    @FXML
    private void initialize() {
        mainPane.widthProperty().addListener(p -> simplePane.setPrefWidth(mainPane.getWidth()));
        mainPane.heightProperty().addListener(p -> scrollPane.setPrefHeight(mainPane.getHeight()));

        windowCheckBox.selectedProperty().addListener(p -> strucTypeListener(windowCheckBox, StructureType.WINDOW_STRUCTURE));
        chessCheckBox.selectedProperty().addListener(p -> strucTypeListener(chessCheckBox, StructureType.CHESS_STRUCTURE));
        lineCheckBox.selectedProperty().addListener(p -> strucTypeListener(lineCheckBox, StructureType.LINE_STRUCTURE));
        randomCheckBox.selectedProperty().addListener(p -> strucTypeListener(randomCheckBox, StructureType.RANDOM_STRUCTURE));

        windowCheckBox.setSelected(true);
        
        modelController = new PatchworkModelController();

        emptyGenerate();
        //paramGenerate();
    }

    private void strucTypeListener(CheckBox checkBox, StructureType structureType) {
        if (checkBox.isSelected()) {
            currentStructureTypeList.add(structureType);
        } else {
            currentStructureTypeList.remove(structureType);    
        }
    }

    private void emptyGenerate() {
        currentSimpleList.clear();
        currentSimpleList.addAll(modelController.generateVariousSimples(null));
        layout(currentSimpleList);
    }

    private void paramGenerate() {
        currentSimpleList.clear();

        modelController.setSize(new Point(getInt(sizeXTextField.getText()), getInt(sizeYTextField.getText())));
        modelController.setPaintStrategy(PaintType.RANDOM_COLOR, new ModelConfig());

        for (StructureType structureType : currentStructureTypeList) {
            modelController.setStructureStrategy(structureType, new ModelConfig());
            for (int i = 0; i < 3; i++) {
                currentSimpleList.add(modelController.generate());
            }
        }
        
        layout(currentSimpleList);
    }

    private void layout(List<BlockSet> blockSets) {
        List<Group> groupList = new ArrayList<>();
        for (BlockSet blockSet : blockSets) {
            groupList.add(createViewBlockSet(blockSet, simpleBlockSize, simpleBlockSize));
        }
        simplePane.getChildren().clear();
        simplePane.getChildren().addAll(groupList);
    }

    private Block getTwoTriangleBlock() {
        Block twoTriangle = new Block();
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,      0,100,   100,0        }, ColorFill.BLUE));
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {100,100,  100,0,   0,100        }, ColorFill.GREEN));
        return twoTriangle;
    }

    @FXML
    void structureChange(ActionEvent event) {
    
    }

    @FXML
    void openColorSet(ActionEvent event) {

    }

    @FXML
    void openImage(ActionEvent event) {

    }

    @FXML
    void openBlockType(ActionEvent event) {

    }

    @FXML
    void generateButtonPress(ActionEvent event) {
        paramGenerate();
    }

    @FXML
    void plusSizeButtonPress(ActionEvent event) {
        simpleBlockSize += 30;
        layout(currentSimpleList);
    }

    @FXML
    void minusSizeButtonPress(ActionEvent event) {
        simpleBlockSize -= 30;
        layout(currentSimpleList);
    }

}
