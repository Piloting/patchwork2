package ru.pilot.patchwork.controller;

import java.util.Set;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelService;
import ru.pilot.patchwork.model.PaintStrategyType;
import ru.pilot.patchwork.model.StructureStrategyType;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategy;
import ru.pilot.patchwork.service.struct.strategy.StructureStrategy;

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
    
    
    // блоки учавствующие в генерации
    public void enableTemplateBlock(Long id){
        modelService.getPatchworkModel().getTemplateBlocks().changeActiveState(id);
    }
    public void disableTemplateBlock(Long id){
        modelService.getPatchworkModel().getTemplateBlocks().changeActiveState(id);
    }
    
    
    // структура
    public void setStructureStrategy(StructureStrategyType structureStrategyType, ModelConfig modelConfig){
        modelService.getPatchworkModel().setStructureStrategy(StructureStrategy.getImplByType(structureStrategyType));
    }
    public void replaceStructure(Long oldTemplateBlockId, IBlock newTemplateBlockId){
        modelService.replaceBlock(oldTemplateBlockId, newTemplateBlockId);
    }

    
    // цвета
    public Set<Paint> getModelPaints(){
       return modelService.getModelPaints();
    }
    public void setPaintStrategy(PaintStrategyType paintStrategyType, ModelConfig modelConfig){
        modelService.getPatchworkModel().setPaintStrategy(PaintStrategy.getImplByType(paintStrategyType));
    }
    public void replacePaint(Paint oldPaint, Paint newPaint){
        modelService.replacePaint(oldPaint, newPaint);
    }
    public void generatePaint(){
        modelService.generatePaint();
    }
}
