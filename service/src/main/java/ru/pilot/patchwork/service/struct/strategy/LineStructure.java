package ru.pilot.patchwork.service.struct.strategy;

import java.util.ArrayList;
import java.util.List;

import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelParam;
import ru.pilot.patchwork.model.StructureType;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.MirrorType;
import ru.pilot.patchwork.service.coord.Point;


/** 
 * Вертикальные или горизонтальные линии.
 * До середины гереруются линии из случайных блоков, далее (от середины до конца) зеркально
 */
public class LineStructure extends StructureStrategy {
    
    @Override
    public BlockSet fill(int blockCountX, int blockCountY, List<IBlock> availableBlockList, ModelConfig config) {
        validate(blockCountX, blockCountY, availableBlockList);

        // расположение из настройки или по длинной стороне
        Boolean isVertical = config.getParam(ModelParam.VERTICAL_LINE);
        isVertical = isVertical == null ? (blockCountX >= blockCountY) : isVertical;

        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        
        BlockSet blockSet = new BlockSet();
        List<BlockSet> blockLineList = new ArrayList<>();
        
        if (isVertical){
            // находим центр, округляем в верх для того что бы сгенерить и центральную полосу
            int center = (int) Math.ceil((double) blockCountX / 2);
            boolean centerWithCopy = center == (blockCountX / 2);

            // проходим от начала до центра - тут генерируем
            // nextX координаты начала для очередного блока
            double nextX = generateVertical(blockCountY, availableBlockList, config, blockSet, center, blockLineList);

            // проходим от центра до конца - тут зеркально копируем
            copyVertical(manipulator, blockSet, center, centerWithCopy, nextX, blockLineList);
        } else {
            // находим центр, округляем в верх для того что бы сгенерить и центральную полосу
            int center = (int) Math.ceil((double) blockCountY / 2);
            boolean centerWithCopy = center == (blockCountY / 2);

            // проходим от начала до центра - тут генерируем
            // nextY координаты начала для очередного блока
            double nextY = generateHorizontal(blockCountX, availableBlockList, config, blockSet, center, blockLineList);

            // проходим от центра до конца - тут зеркально копируем
            copyHorizontal(manipulator, blockSet, center, centerWithCopy, nextY, blockLineList);
        }
        
        return blockSet;
    }

    private void copyHorizontal(BlockPointManipulator manipulator, BlockSet blockSet, int center, boolean centerWithCopy, double nextY, List<BlockSet> blockLineList) {
        for (int i = center-1; i >= 0; i--) {
            if (i == center-1 && !centerWithCopy){
                continue;
            }
            // вытащим сгенеренный блок
            BlockSet line = blockLineList.get(i);
            BlockSet copy = line.copyToNew();
            // зеркалируем
            manipulator.mirror(copy, MirrorType.VERTICAL);
            // расчитаем координаты для вставки. Это координата левой стороны блока
            Point blockMinY = CoordUtils.getMin(copy.getPolygonBlocks());
            // блок надо поместить в точке nextY - для этого укажем недостоющее расстояние до этой точки
            blockSet.addBlock(copy, 0, nextY-blockMinY.getY());
            // запоминаем, где размещать следующий блок
            nextY = CoordUtils.getMax(blockSet.getPolygonBlocks()).getY();
        }
    }

    private double generateHorizontal(int blockCountX, List<IBlock> availableBlockList, ModelConfig config, BlockSet blockSet, int center, List<BlockSet> blockLineList) {
        RandomStructure randomStructure = new RandomStructure();
        double nextY = 0;
        // проходим от начала до центра - тут генерируем
        for (int i = 0; i <= center-1; i++) {
            // левая полоса
            BlockSet line = randomStructure.fill(blockCountX, 1, availableBlockList, config);
            blockLineList.add(line);
            blockSet.addBlock(line, 0, nextY);

            // получим дальние точки для размещения следующих блоков
            Point max = CoordUtils.getMax(line.getPolygonBlocks());
            // запоминаем, где размещать следующий блок
            nextY = max.getY();
        }
        return nextY;
    }

    private void copyVertical(BlockPointManipulator manipulator, BlockSet blockSet, int center, boolean centerWithCopy, double nextX, List<BlockSet> blockLineList) {
        for (int i = center-1; i >= 0; i--) {
            if (i == center-1 && !centerWithCopy){
                continue;
            }
            // вытащим сгенеренный блок
            BlockSet line = blockLineList.get(i);
            BlockSet copy = line.copyToNew();
            // зеркалируем
            manipulator.mirror(copy, MirrorType.HORIZONTAL);
            // расчитаем координаты для вставки. Это координата левой стороны блока
            Point blockMinX = CoordUtils.getMin(copy.getPolygonBlocks());
            // блок надо поместить в точке nextX - для этого укажем недостоющее расстояние до этой точки
            blockSet.addBlock(copy, nextX-blockMinX.getX(), 0);
            // запоминаем, где размещать следующий блок
            nextX = CoordUtils.getMax(blockSet.getPolygonBlocks()).getX();
        }
    }

    private double generateVertical(int blockCountY, List<IBlock> availableBlockList, ModelConfig config, BlockSet blockSet, int center, List<BlockSet> blockLineList) {
        RandomStructure randomStructure = new RandomStructure();
        double nextX = 0;
        // проходим от начала до центра - тут генерируем
        for (int i = 0; i <= center-1; i++) {
            // левая полоса
            BlockSet line = randomStructure.fill(1, blockCountY, availableBlockList, config);
            blockLineList.add(line);
            blockSet.addBlock(line, nextX, 0);
            
            // получим дальние точки для размещения следующих блоков
            Point max = CoordUtils.getMax(line.getPolygonBlocks());
            // запоминаем, где размещать следующий блок
            nextX = max.getX();
        }
        return nextX;
    }
    
    @Override
    protected StructureType getType() {
        return StructureType.LINE_STRUCTURE;
    }
}
