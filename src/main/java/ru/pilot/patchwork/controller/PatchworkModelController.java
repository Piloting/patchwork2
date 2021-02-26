package ru.pilot.patchwork.controller;

import java.util.List;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.PaintStrategyType;
import ru.pilot.patchwork.model.StructureStrategyType;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.Paint;

public interface PatchworkModelController {

    // сама модель
    void setSize(Point xy);
    Point getSize();
    BlockSet getCurrentModelBlock();
    void setCurrentModelBlock(BlockSet blockSet);
    BlockSet generate();
    
    // блоки учавствующие в генерации
    void enableTemplateBlock(Long id);
    void disableTemplateBlock(Long id);
    
    // структура
    void setStructureStrategy(StructureStrategyType structureStrategyType, ModelConfig modelConfig);
    void replaceStructure(Long id, IBlock block);
    void generateStructure();

    // цвета
    List<Paint> getModelPaints();
    void setPaintStrategy(PaintStrategyType paintStrategyType, ModelConfig modelConfig);
    void replacePaint(Paint oldPaint, Paint newPaint);
    void generatePaint();
}
