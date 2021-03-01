package ru.pilot.patchwork.block;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.dao.mem.BlockMemoryDao;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.struct.strategy.LineStructure;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelParam;

public class LineStructureTest {
    
    @Test
    public void printTestVertical(){
        int blockCountX = 5;
        int blockCountY = 5;

        LineStructure lineStructure = new LineStructure();
        List<IBlock> templateList = new BlockMemoryDao().getTemplateList();

        ModelConfig config = new ModelConfig();
        config.addParam(ModelParam.VERTICAL_LINE, true);
        BlockSet blockSet = lineStructure.fill(blockCountX, blockCountY, templateList, config);
        TextPrinterBlock.print(blockSet);
        
        Assertions.assertEquals(blockSet.getBlocks().size(), blockCountX*blockCountY);
    }
    
    @Test
    public void printTestHorizontal(){
        int blockCountX = 4;
        int blockCountY = 4;

        LineStructure lineStructure = new LineStructure();
        List<IBlock> templateList = new BlockMemoryDao().getTemplateList();

        ModelConfig config = new ModelConfig();
        config.addParam(ModelParam.VERTICAL_LINE, false);
        BlockSet blockSet = lineStructure.fill(blockCountX, blockCountY, templateList, config);
        TextPrinterBlock.print(blockSet);
        
        Assertions.assertEquals(blockSet.getBlocks().size(), blockCountX*blockCountY);
    }
}
