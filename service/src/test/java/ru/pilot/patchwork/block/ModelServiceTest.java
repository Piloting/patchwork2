package ru.pilot.patchwork.block;

import java.util.List;

import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.model.ModelService;
import ru.pilot.patchwork.service.block.BlockSet;

public class ModelServiceTest {
    
    @Test
    public void testSimpleGenerate(){
        ModelService modelService = new ModelService();
        List<BlockSet> blockSets = modelService.generateVariousSimples(null);
        for (BlockSet blockSet : blockSets) {
            TextPrinterBlock.print(blockSet);
        }
    }
}
