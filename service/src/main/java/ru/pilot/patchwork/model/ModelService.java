package ru.pilot.patchwork.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategy;
import ru.pilot.patchwork.service.paint.strategy.RandomColor;
import ru.pilot.patchwork.service.struct.strategy.ChessStructure;
import ru.pilot.patchwork.service.struct.strategy.LineStructure;
import ru.pilot.patchwork.service.struct.strategy.RandomStructure;
import ru.pilot.patchwork.service.struct.strategy.StructureStrategy;
import ru.pilot.patchwork.service.struct.strategy.WindowStructure;

public class ModelService {
    
    @Getter
    private final PatchworkModel patchworkModel;

    public ModelService(){
        patchworkModel = new PatchworkModel();
    }
    public ModelService(PatchworkModel patchworkModel){
        this.patchworkModel = patchworkModel;
    }
    
    public BlockSet generate(){
        return generateByParam(
                patchworkModel.getSizeModel(), 
                patchworkModel.getStructureStrategy(),
                patchworkModel.getPaintStrategy(),
                patchworkModel.getTemplateBlocks().getActiveBlocks(),
                patchworkModel.getModelConfig());
    }

    public BlockSet generateByParam(Point sizeModel, StructureStrategy structureStrategy, PaintStrategy paintStrategy, List<IBlock> templateBlocks, ModelConfig modelConfig){
        BlockSet blockSet = structureStrategy.fill((int) sizeModel.getX(), (int) sizeModel.getY(), templateBlocks, modelConfig);
        paintStrategy.fill(blockSet, modelConfig);
        return blockSet;
    }

    public List<BlockSet> generateSimples(){
        List<BlockSet> blockSets = new ArrayList<>(9);
        // при повторной генерации блоки и цвета будут отличаться
        for (int i = 0; i < 10; i++) {
            blockSets.add(generate());
        }
        return blockSets;
    }
    
    public List<BlockSet> generateVariousSimples(PaintSet paintSet){
        List<BlockSet> blockSets = new ArrayList<>(9);
        // при повторной генерации блоки и цвета будут отличаться
        for (int i = 0; i < 5; i++) {
            blockSets.addAll(getSimpleModels());
        }
        PaintStrategy paintStrategy = new RandomColor(paintSet);
        for (BlockSet blockSet : blockSets) {
            paintStrategy.fill(blockSet, new ModelConfig());
        }
        return blockSets;
    }

    private List<BlockSet> getSimpleModels(){
        List<IBlock> templateList = patchworkModel.getTemplateBlocks().getActiveBlocks();
        List<BlockSet> blockSets = new ArrayList<>(4*3);
        genSimples(blockSets, templateList, new ChessStructure());
        genSimples(blockSets, templateList, new LineStructure());
        genSimples(blockSets, templateList, new RandomStructure());
        genSimples(blockSets, templateList, new WindowStructure());
        return blockSets;
    }

    private void genSimples(List<BlockSet> blockSets, List<IBlock> templateList, StructureStrategy structureStrategy) {
        blockSets.add(structureStrategy.fill(4,4, templateList, new ModelConfig()));
        blockSets.add(structureStrategy.fill(5,5, templateList, new ModelConfig()));
        blockSets.add(structureStrategy.fill(6,6, templateList, new ModelConfig()));
    }

    public void replaceBlock(Long oldTemplateBlockId, IBlock newTemplateBlockId) {
        // todo
    }

    public Set<Paint> getModelPaints() {
        BlockSet currentModel = patchworkModel.getCurrentModel();
        Set<Paint> paints = new HashSet<>();
        for (PolygonBlock polygonBlock : currentModel.getPolygonBlocks()) {
            paints.add(polygonBlock.getPaint());
        }
        return paints;
    }

    public void replacePaint(Paint oldPaint, Paint newPaint) {
        // todo
    }

    public void generatePaint() {
        patchworkModel.getPaintStrategy().fill(patchworkModel.getCurrentModel(), patchworkModel.getModelConfig());
    }
    public void setModelConfig(ModelConfig modelConfig) {
        patchworkModel.setModelConfig(modelConfig);
    }
}
