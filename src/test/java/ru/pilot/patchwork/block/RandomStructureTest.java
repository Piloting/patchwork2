package ru.pilot.patchwork.block;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.dao.mem.BlockMemoryDao;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.struct.strategy.RandomStructure;
import ru.pilot.patchwork.model.ModelConfig;

public class RandomStructureTest {
    
    @Test
    public void printTest(){
        int blockCountX = 5;
        int blockCountY = 3;
        
        RandomStructure randomStructure = new RandomStructure();
        List<IBlock> templateList = new BlockMemoryDao().getTemplateList();
        
        BlockSet blockSet = randomStructure.fill(blockCountX, blockCountY, templateList, new ModelConfig());
        TextPrinterBlock.print(blockSet);
        
        Assertions.assertEquals(blockSet.getBlocks().size(), blockCountX*blockCountY);
    }
}
