package ru.pilot.patchwork.service.struct.strategy;

import java.util.List;

import liquibase.util.StringUtils;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.MirrorType;
import ru.pilot.patchwork.service.coord.Point;


/** 
 * Оконная структрура - 4 окошка и рама между ними.
 *  ---- .  ----
 * |   | . |   |
 * ----  . ----
 * . . . 0 . . .
 *  ---- .  ---- 
 * |   | . |   |
 * ----  . ---- 
 * Генерится одно окошко и зеркалируется в 3 других места, рама(разеделители) может отсутствовать
 * 
 * Размер и наличие рамы задается через настройки
 */
public class WindowStructure implements StructureStrategy {
    
    @Override
    public BlockSet fill(int blockCountX, int blockCountY, List<IBlock> availableBlockList, StructureConfig config) {
        validate(blockCountX, blockCountY, availableBlockList);
        
        // настройки размера рамы
        int delimiterPercentX = config.getParam(StructureParam.VERTICAL_DELIMITER, 0);
        int delimiterPercentY = config.getParam(StructureParam.HORIZONTAL_DELIMITER, 0);
        // по % рамы вычислим % на 1 окно
        int windowPercentX = (100 - delimiterPercentX) / 2;
        int windowPercentY = (100 - delimiterPercentY) / 2;

        // размер окна
        int windowBlockCountX = Math.max(blockCountX * windowPercentX / 100, 1);
        int windowBlockCountY = Math.max(blockCountY * windowPercentY / 100, 1);
        
        // размер рамы - вертикальная часть 
        int vertFrameBlockCountX = Math.max(blockCountX - windowBlockCountX * 2 , 0);
        int vertFrameBlockCountY = windowBlockCountY;

        // размер рамы - горизонтальная часть 
        int horizontFrameBlockCountX = windowBlockCountX;
        int horizontFrameBlockCountY = Math.max(blockCountY - windowBlockCountY * 2 , 0);
        
        // размер рамы - точка пересечения
        int pointFrameBlockCountX = vertFrameBlockCountX;
        int pointFrameBlockCountY = horizontFrameBlockCountY;
        
        paint(blockCountX,              blockCountY,
                windowBlockCountX,        windowBlockCountY,
              vertFrameBlockCountX,     vertFrameBlockCountY,
              horizontFrameBlockCountX, horizontFrameBlockCountY,
              pointFrameBlockCountX,    pointFrameBlockCountY);
        
        BlockSet blockSet = generateBlocks(availableBlockList, config,
                blockCountX,              blockCountY,
                windowBlockCountX,        windowBlockCountY, 
                vertFrameBlockCountX,     vertFrameBlockCountY, 
                horizontFrameBlockCountX, horizontFrameBlockCountY, 
                pointFrameBlockCountX,    pointFrameBlockCountY);

        
        return blockSet;
    }


    private BlockSet generateBlocks(List<IBlock> availableBlockList, StructureConfig config,
                                    int blockCountX,              int blockCountY,
                                    int windowBlockCountX,        int windowBlockCountY,
                                    int vertFrameBlockCountX,     int vertFrameBlockCountY,
                                    int horizontFrameBlockCountX, int horizontFrameBlockCountY,
                                    int pointFrameBlockCountX,    int pointFrameBlockCountY) {
        
        RandomStructure randomStructure = new RandomStructure();
        LineStructure lineStructure = new LineStructure();
        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();

        BlockSet windowBlock = null;
        BlockSet vertFrameBlock = null;
        BlockSet horizontFrameBlock = null;
        BlockSet pointFrameBlock = null;

        // ********* ГЕНЕРАЦИЯ БЛОКОВ *********
        
        // генерируем окно
        if (windowBlockCountX > 0 && windowBlockCountY > 0){
            windowBlock = randomStructure.fill(windowBlockCountX, windowBlockCountY, availableBlockList, config);
        }

        // генерируем вертикальную часть рамы
        if (vertFrameBlockCountX > 0 && vertFrameBlockCountY > 0){
            StructureConfig vertLineConfig = new StructureConfig();
            vertLineConfig.addParam(StructureParam.VERTICAL_LINE, true);
            vertFrameBlock = lineStructure.fill(vertFrameBlockCountX, vertFrameBlockCountY, availableBlockList, vertLineConfig);
        }

        // генерируем горизонтальную часть рамы
        if (horizontFrameBlockCountX > 0 && horizontFrameBlockCountY > 0){
            StructureConfig horizontLineConfig = new StructureConfig();
            horizontLineConfig.addParam(StructureParam.VERTICAL_LINE, false);
            horizontFrameBlock = lineStructure.fill(horizontFrameBlockCountX, horizontFrameBlockCountY, availableBlockList, horizontLineConfig);
        }

        // генерируем точку рамы
        if (pointFrameBlockCountX > 0 && pointFrameBlockCountY > 0){
            WindowStructure windowStructure = new WindowStructure();
            pointFrameBlock = windowStructure.fill(pointFrameBlockCountX, pointFrameBlockCountY, availableBlockList, new StructureConfig());
        }

        
        // ********* КОМПОНОВКА БЛОКОВ *********
        
        BlockSet blockSet = new BlockSet();
        
        // ----- . -----
        // |   | . |   |
        // ----- . -----
        double currentX = 0;
        double currentY = 0;
        Point windowBlockSize        = getBlockSize(windowBlock);
        Point vertFrameBlockSize     = getBlockSize(vertFrameBlock);
        Point horizontFrameBlockSize = getBlockSize(horizontFrameBlock);
        Point pointFrameBlockSize    = getBlockSize(pointFrameBlock);
        
        // левое верхнее окно
        if (windowBlock != null){
            blockSet.addBLock(windowBlock.copyToNew(), currentX, currentY);
            currentX += windowBlockSize.getX();
        }
        // вертикальная рама между верхними окнами
        if (vertFrameBlock != null && blockCountX > 1){
            blockSet.addBLock(vertFrameBlock.copyToNew(), currentX, 0);
            currentX += vertFrameBlockSize.getX();
        }
        // правое верхнее окно
        if (windowBlock != null && blockCountX > 1){
            BlockSet copy = windowBlock.copyToNew();
            manipulator.mirror(copy, MirrorType.HORIZONTAL);
            blockSet.addBLock(copy, currentX, 0);
        }
        
        //  . . . 0 . . .
        
        // Левая часть горизонтальной рамы между верхними и нижними окнами
        currentX = 0;
        currentY = CoordUtils.getMax(blockSet.getPolygonBlocks()).getY();
        if (horizontFrameBlock != null){
            blockSet.addBLock(horizontFrameBlock.copyToNew(), currentX, currentY);
            currentX += horizontFrameBlockSize.getX();
        }
        // точка рамы между верхними и нижними окнами
        if (pointFrameBlock != null){
            blockSet.addBLock(pointFrameBlock.copyToNew(), currentX, currentY);
            currentX += pointFrameBlockSize.getX();
        }
        // Правая часть горизонтальной рамы между верхними и нижними окнами  
        if (horizontFrameBlock != null && blockCountX > 1){
            BlockSet copy = horizontFrameBlock.copyToNew();
            manipulator.mirror(copy, MirrorType.HORIZONTAL);
            blockSet.addBLock(copy, currentX, currentY);
        }
        
        // ----- . -----
        // |   | . |   |
        // ----- . -----
        
        // левое нижнее окно
        currentX = 0;
        currentY = CoordUtils.getMax(blockSet.getPolygonBlocks()).getY();
        if (windowBlock != null && blockCountY > 1){
            BlockSet copy = windowBlock.copyToNew();
            manipulator.mirror(copy, MirrorType.VERTICAL);
            blockSet.addBLock(copy, currentX, currentY);
            currentX += windowBlockSize.getX();
        }
        // вертикальная рама между нижними окнами
        if (vertFrameBlock != null && blockCountY > 1){
            BlockSet copy = vertFrameBlock.copyToNew();
            manipulator.mirror(copy, MirrorType.VERTICAL);
            blockSet.addBLock(copy, currentX, currentY);
            currentX += vertFrameBlockSize.getX();
        }
        // правое верхнее окно
        if (windowBlock != null && blockCountY > 1 && blockCountX > 1){
            BlockSet copy = windowBlock.copyToNew();
            manipulator.mirror(copy, MirrorType.DIAGONAL);
            blockSet.addBLock(copy, currentX, currentY);
        }
        
        return blockSet;
    }

    private Point getBlockSize(BlockSet block) {
        return block != null ? CoordUtils.getSize(block.getPolygonBlocks()) : new Point(0, 0);
    }


    private void paint(
            int blockCountX,              int blockCountY,
            int windowBlockCountX,        int windowBlockCountY,
            int vertFrameBlockCountX,     int vertFrameBlockCountY,
            int horizontFrameBlockCountX, int horizontFrameBlockCountY,
            int pointFrameBlockCountX,    int pointFrameBlockCountY){
        
        String window = " . ";
        String frame  = " X ";
        String center = " 0 ";

        System.out.println(StringUtils.repeat("-", window.length() * (blockCountX)));

        pseudoPrint(windowBlockCountX, vertFrameBlockCountX, windowBlockCountY, blockCountX, window, frame);
        pseudoPrint(horizontFrameBlockCountX, pointFrameBlockCountX, horizontFrameBlockCountY, blockCountX, frame, center);
        if (blockCountY > 1) {
            pseudoPrint(windowBlockCountX, vertFrameBlockCountX, windowBlockCountY, blockCountX, window, frame);
        }
        
        System.out.println(StringUtils.repeat("-", window.length() * (blockCountX)));
    }
    
    private void pseudoPrint(int windowBlockCountX, int vertFrameBlockCountX, int windowBlockCountY, int blockCountX, String window, String frame) {
        for (int i = 0; i < windowBlockCountY; i++) {
            for (int j = 0; j < windowBlockCountX; j++) {
                System.out.print(window);
            }
            for (int j = 0; j < vertFrameBlockCountX; j++) {
                System.out.print(frame);
            }
            for (int j = 0; j < windowBlockCountX && blockCountX>1; j++) {
                System.out.print(window);
            }
            System.out.println();
        }
    }

}