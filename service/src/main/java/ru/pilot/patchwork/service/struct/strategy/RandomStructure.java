package ru.pilot.patchwork.service.struct.strategy;
import java.util.List;
import java.util.Random;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.StructureStrategyType;
import ru.pilot.patchwork.service.block.BlockIdGenerator;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.Point;

public class RandomStructure extends StructureStrategy {
    private static final Random rnd = new Random();
    
    @Override
    public BlockSet fill(int blockCountX, int blockCountY, List<IBlock> availableBlockList, ModelConfig config) {
        validate(blockCountX, blockCountY, availableBlockList);

        int availableBlockCount = availableBlockList.size();
        BlockSet blockSet = new BlockSet();
        
        // координаты начала для очередного блока
        double nextX = 0;
        double nextY = 0;
        
        for (int y = 0; y < blockCountY; y++) {

            double localNextY = 0;
            for (int x = 0; x < blockCountX; x++) {
                // делаем новый блок
                IBlock block = getNewBlock(availableBlockList, availableBlockCount);
                
                // добавляем в нужное место
                blockSet.addBLock(block, nextX, nextY);
                
                // получим дальние точки для размещения следующих блоков
                Point max = CoordUtils.getMax(block.getPolygonBlocks());

                // запоминаем, где размещать следующий блок
                nextX = max.getX();
                localNextY = Math.max(localNextY, max.getY());
            }
            nextX = 0;
            nextY = localNextY;
        }
        
        return blockSet;
    }

    private IBlock getNewBlock(List<IBlock> availableBlockList, int availableBlockCount) {
        IBlock template = availableBlockList.get(rnd.nextInt(availableBlockCount));
        IBlock block = template.copyToNew();
        // проставим у всех разный признак похожести
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            polygonBlock.setSimilarId(BlockIdGenerator.getId());
        }
        return block;
    }
    
    @Override
    protected StructureStrategyType getType() {
        return StructureStrategyType.ChessStructure;
    }
}
