package ru.pilot.patchwork.block;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.dao.mem.BlockMemoryDao;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.struct.strategy.ChessStructure;
import ru.pilot.patchwork.model.ModelConfig;

public class ChessStructureTest {
    
    @Test
    public void printTestVertical(){
        int blockCountX = 2;
        int blockCountY = 3;

        ChessStructure chessStructure = new ChessStructure();
        List<IBlock> templateList = new BlockMemoryDao().getTemplateList();

        BlockSet blockSet = chessStructure.fill(blockCountX, blockCountY, templateList, new ModelConfig());
        TextPrinterBlock.print(blockSet);
        
        Assertions.assertEquals(blockSet.getBlocks().size(), blockCountX*blockCountY);
    }
    
}
