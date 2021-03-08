package ru.pilot.patchwork.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lombok.SneakyThrows;
import ru.pilot.patchwork.MainFx;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelParam;
import ru.pilot.patchwork.model.PaintType;
import ru.pilot.patchwork.model.StructureType;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.ImageFill;
import ru.pilot.patchwork.service.paint.PaintSet;
import ru.pilot.patchwork.utils.FileUtils;

public class SimplesController extends ParentController {

    private PatchworkModelController modelController;
    
    private int simpleBlockSize = 300;
    private final List<BlockSet> currentSimpleList = new ArrayList<>();
    private final List<StructureType> currentStructureTypeList = new ArrayList<>();
    private final Map<Long, PaintSet> selectedPaintMap = new HashMap<>();
    private ImageFill selectedImageFill = null;
    
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

        ModelConfig modelConfig = new ModelConfig();

        if (selectedImageFill == null){
            modelConfig.addParam(ModelParam.PAINT_SET, getSelectedPaintSet());
            modelController.setPaintStrategy(PaintType.RANDOM_COLOR);
        } else {
            modelConfig.addParam(ModelParam.PICTURE, selectedImageFill);
            modelController.setPaintStrategy(PaintType.PICTURE_COLOR);
        }

        modelController.setModelConfig(modelConfig);
        for (StructureType structureType : currentStructureTypeList) {
            modelController.setStructureStrategy(structureType, modelConfig);
            for (int i = 0; i < 4; i++) {
                currentSimpleList.add(modelController.generate());
            }
        }
        layout(currentSimpleList);
    }
    
    private PaintSet getSelectedPaintSet() {
        if (selectedPaintMap.isEmpty()){
            return null;
        }
        PaintSet complexPaintSet = new PaintSet();
        for (PaintSet paintSet : selectedPaintMap.values()) {
            complexPaintSet.getPaints().addAll(paintSet.getPaints());
        }
        return complexPaintSet;
    }

    private void layout(List<BlockSet> blockSets) {
        List<Group> groupList = new ArrayList<>();
        for (BlockSet blockSet : blockSets) {
            groupList.add(createViewBlockSet(blockSet, simpleBlockSize, simpleBlockSize));
        }
        simplePane.getChildren().clear();
        simplePane.getChildren().addAll(groupList);
    }

    @FXML
    void structureChange(ActionEvent event) {
    
    }

    @FXML
    void openColorSet(ActionEvent event) {
        ColorSetController.setSelectedPaintSetIds(selectedPaintMap);
        openForm("colorSet.fxml", "Наборы цветов", true);
        Map<Long, PaintSet> fromSelectedPaintMap = ColorSetController.getSelectedPaintSetIds();
        if (!fromSelectedPaintMap.isEmpty()){
            selectedPaintMap.clear();
            selectedPaintMap.putAll(fromSelectedPaintMap);
            selectedImageFill = null;
        }
    }

    @SneakyThrows
    @FXML
    void openImage(ActionEvent event) {
        File file = FileUtils.getPictFileChooser().showOpenDialog(MainFx.getPrimaryStage());
        if (file != null){
            selectedPaintMap.clear();
            selectedImageFill = new ImageFill(file.getAbsolutePath(), Files.readAllBytes(file.toPath()));
        } else {
            selectedImageFill = null;
        }
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
