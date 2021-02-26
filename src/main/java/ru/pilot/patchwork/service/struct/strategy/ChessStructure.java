package ru.pilot.patchwork.service.struct.strategy;

import java.util.List;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.Point;


/** 
 * Шахматная структура.
 * Если 2 блока, то стандартная шахматка, если больше, то просто последовательное чередование блоков.
 */
public class ChessStructure implements StructureStrategy {
    
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
                
                IBlock block = getNewBlock(availableBlockList, availableBlockCount, x+y);

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

    private IBlock getNewBlock(List<IBlock> availableBlockList, int availableBlockCount, int itemNumber) {
        int num = itemNumber % availableBlockCount;
        IBlock template = availableBlockList.get(num);
        IBlock block = template.copyToNew();
        // проставим у всех признак похожести
        int i = 0;
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            polygonBlock.setSimilarId((long) num + i++);
        }
        return block;
    }
}
