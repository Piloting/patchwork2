package ru.pilot.patchwork.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelService;
import ru.pilot.patchwork.model.PaintType;
import ru.pilot.patchwork.model.StructureType;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategy;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategyEnum;
import ru.pilot.patchwork.service.struct.strategy.StructureStrategyEnum;

// single mode
public class PatchworkModelController {

    // lazy 
    private ModelService modelService = new ModelService();
    
    // сама модель
    public void setSize(Point size){
        modelService.getPatchworkModel().setSizeModel(size);
    }
    public Point getSize(){
        return modelService.getPatchworkModel().getSizeModel();
    }
    public BlockSet getCurrentModelBlock(){
        return modelService.getPatchworkModel().getCurrentModel();
    }
    public void setCurrentModelBlock(BlockSet blockSet){
        modelService.getPatchworkModel().setCurrentModel(blockSet);
    }
    public BlockSet generate(){
        BlockSet generate = modelService.generate();
        setCurrentModelBlock(generate);
        return generate;
    }
    public void setModelConfig(ModelConfig modelConfig){
        modelService.setModelConfig(modelConfig);
    }
    
    
    // блоки учавствующие в генерации
    public void enableTemplateBlock(Long id){
        modelService.getPatchworkModel().getTemplateBlocks().changeActiveState(id);
    }
    public void disableTemplateBlock(Long id){
        modelService.getPatchworkModel().getTemplateBlocks().changeActiveState(id);
    }
    
    
    // структура
    public void setStructureStrategy(StructureType structureType, ModelConfig modelConfig){
        modelService.getPatchworkModel().setStructureStrategy(StructureStrategyEnum.getImplByType(structureType));
    }
    public void replaceStructure(Long oldTemplateBlockId, IBlock newTemplateBlockId){
        modelService.replaceBlock(oldTemplateBlockId, newTemplateBlockId);
    }
    public List<StructureType> getAvailableStructureType(){
        List<StructureType> typeList = new ArrayList<>();
        for (StructureStrategyEnum value : StructureStrategyEnum.values()) {
            typeList.add(value.getType());
        }
        return typeList;
    }

    
    // цвета
    public Set<Paint> getModelPaints(){
       return modelService.getModelPaints();
    }
    public void setPaintStrategy(PaintType paintType){
        modelService.getPatchworkModel().setPaintStrategy(PaintStrategyEnum.getImplByType(paintType));
    }
    public void replacePaint(Paint oldPaint, Paint newPaint){
        modelService.replacePaint(oldPaint, newPaint);
    }
    public void generatePaint(){
        modelService.generatePaint();
    }
    public List<PaintType> getAvailablePaintType(){
        List<PaintType> typeList = new ArrayList<>();
        for (PaintStrategyEnum value : PaintStrategyEnum.values()) {
            typeList.add(value.getType());
        }
        return typeList;
    }


    public List<BlockSet> generateVariousSimples(PaintSet paintSet){
        return modelService.generateVariousSimples(paintSet);
    }
}
