package ru.pilot.patchwork.block;

import java.util.List;

import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.dao.mem.BlockMemoryDao;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelParam;
import ru.pilot.patchwork.service.struct.strategy.WindowStructure;

public class WindowStructureTest {
    
    @Test
    public void windowStructureWithoutFrameConfig(){
        int blockCountX = 3;
        int blockCountY = 3;

        WindowStructure windowStructure = new WindowStructure();
        List<IBlock> templateList = new BlockMemoryDao().getTemplateList();

        BlockSet blockSet = windowStructure.fill(blockCountX, blockCountY, templateList, new ModelConfig());
        TextPrinterBlock.print(blockSet);
    }
    
    @Test
    public void windowStructureWithFrameConfig(){
        int blockCountX = 10;
        int blockCountY = 10;

        WindowStructure windowStructure = new WindowStructure();
        List<IBlock> templateList = new BlockMemoryDao().getTemplateList();

        ModelConfig config = new ModelConfig();
        config.addParam(ModelParam.VERTICAL_DELIMITER, 30);
        config.addParam(ModelParam.HORIZONTAL_DELIMITER, 30);
        BlockSet blockSet = windowStructure.fill(blockCountX, blockCountY, templateList, config);
        TextPrinterBlock.print(blockSet);
    }
    
}
