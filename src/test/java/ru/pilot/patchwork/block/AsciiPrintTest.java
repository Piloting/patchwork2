package ru.pilot.patchwork.block;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.block.PolygonType;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;

public class AsciiPrintTest {
    
    @Test
    public void printTest(){
        Block block = new Block();
        block.getPolygonBlocks().add(new PolygonBlock(PolygonType.TRIANGLE.getSimplePoints(), ColorFill.BLUE));
        block.getPolygonBlocks().add(new PolygonBlock(PolygonType.RHOMBUS.getSimplePoints(), ColorFill.BLUE));
        block.getPolygonBlocks().add(new PolygonBlock(PolygonType.RECT.getSimplePoints(), ColorFill.BLUE));
        block.getPolygonBlocks().add(new PolygonBlock(PolygonType.TRAPEZOID.getSimplePoints(), ColorFill.BLUE));

        String print = TextPrinterBlock.print(block);
        checkAsciiPrint(print, getExpected(), "Ошибка печати");
    }

        private String getExpected(){
        return  "...........\n" +
                "..  . .  ..\n" +
                ". ..   .. .\n" +
                ". . .  .. .\n" +
                "..   ..  ..\n" +
                ".    ..   .\n" +
                "..  .  ....\n" +
                ". ..    ...\n" +
                ". ..   ....\n" +
                ".. .....  .\n" +
                "...........";
    }
    
    public static void checkAsciiPrint(String actual, String expected, String msg){
        String[] expectedArray = expected.split("\n");
        String[] actualArray = actual.split("\n");
        for (int i = 0; i < actualArray.length; i++) {
            Assertions.assertEquals(actualArray[i].trim(), expectedArray[i].trim(), msg);
        }
    }
    
}
