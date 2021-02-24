package ru.pilot.patchwork.block;

import java.util.List;

import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.MirrorType;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;

public class BlockSetTest {
    
    @Test
    public void createTest(){
        BlockSet blockSet = createBlockSet();

        String print = TextPrinterBlock.print(blockSet);
        AsciiPrintTest.checkAsciiPrint(print, getExpectedCreate(), "Ошибка формирования BlockSet");
    }

    private BlockSet createBlockSet() {
        Block twoTriangle = getTwoTriangleBlock();

        Block rect = getRectBlock();

        BlockSet blockSet = new BlockSet();
        blockSet.addBLock(twoTriangle, 0, 0);
        blockSet.addBLock(rect, 10, 10);
        return blockSet;
    }

    private String getExpectedCreate(){
        return  "...........          \n" +
                ".        ..          \n" +
                ".       . .          \n" +
                ".      .  .          \n" +
                ".     .   .          \n" +
                ".    .    .          \n" +
                ".   .     .          \n" +
                ".  .      .          \n" +
                ". .       .          \n" +
                "..        .          \n" +
                ".....................\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          .         .\n" +
                "          ...........\n";
    }
    
    private String getExpectedManipulate(){
        return  "...........          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".         .          \n" +
                ".....................\n" +
                "          .        ..\n" +
                "          .       . .\n" +
                "          .      .  .\n" +
                "          .     .   .\n" +
                "          .    .    .\n" +
                "          .   .     .\n" +
                "          .  .      .\n" +
                "          . .       .\n" +
                "          ..        .\n" +
                "          ...........";
    }

    @Test
    public void manipulateTest(){
        BlockSet blockSet = createBlockSet();
        TextPrinterBlock.print(blockSet);

        List<PolygonBlock> polygonBlocks = blockSet.getPolygonBlocks();
        
        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        manipulator.mirror(polygonBlocks, MirrorType.DIAGONAL);

        String print = TextPrinterBlock.print(polygonBlocks);

        AsciiPrintTest.checkAsciiPrint(print, getExpectedManipulate(), "Ошибка преобразования BlockSet");
    }

    private Block getRectBlock() {
        Block rect = new Block();
        rect.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,   0,10,  10,10,  10,0}, ColorFill.BLUE));
        return rect;
    }

    private Block getTwoTriangleBlock() {
        Block twoTriangle = new Block();
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,    0,10,   10,0        }, ColorFill.BLUE));
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {10,10,  10,0,   0,10        }, ColorFill.BLUE));
        return twoTriangle;
    }
}
