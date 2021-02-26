package ru.pilot.patchwork.block;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.dao.constant.BlockConstDao;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.config.JacksonConfig;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.service.struct.strategy.WindowStructure;
import ru.pilot.patchwork.service.utils.YmlUtils;

public class SerializeTest {
    
    @Test
    public void toYmlTest(){
        YmlUtils.setObjectMapper(new JacksonConfig().objectMapper());

        BlockSet blockSet = createBlockSet();
        String expected = TextPrinterBlock.print(blockSet);

        String yml = YmlUtils.toYml(blockSet);
        IBlock block = YmlUtils.fromYml(yml);
        String actual = TextPrinterBlock.print(block);
        
        Assertions.assertEquals(actual.trim(), expected.trim(), "Вид должен совпадать!");
    }
    
    @Test
    public void toYmlTest2(){
        YmlUtils.setObjectMapper(new JacksonConfig().objectMapper());

        BlockSet blockSet = createBlockSet2();
        String expected = TextPrinterBlock.print(blockSet);

        String yml = YmlUtils.toYml(blockSet);
        IBlock block = YmlUtils.fromYml(yml);
        String actual = TextPrinterBlock.print(block);
        
        Assertions.assertEquals(actual.trim(), expected.trim(), "Вид должен совпадать!");
    }

    private BlockSet createBlockSet2() {
        return new WindowStructure().fill(
                5, 5,
                new BlockConstDao().getTemplateList(),
                new ModelConfig());
    }


    private Block getRectBlock() {
        Block rect = new Block();
        rect.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,   0,10,  10,10,  10,0}, ColorFill.BLUE));
        return rect;
    }
    
    private BlockSet createBlockSet() {
        Block twoTriangle = getTwoTriangleBlock();

        Block rect = getRectBlock();

        BlockSet blockSet = new BlockSet();
        blockSet.addBLock(twoTriangle, 0, 0);
        blockSet.addBLock(rect, 10, 10);
        return blockSet;
    }
    
    private Block getTwoTriangleBlock() {
        Block twoTriangle = new Block();
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,    0,10,   10,0        }, ColorFill.BLUE));
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {10,10,  10,0,   0,10        }, ColorFill.BLUE));
        return twoTriangle;
    }
}
