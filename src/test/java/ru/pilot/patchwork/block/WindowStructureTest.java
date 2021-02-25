package ru.pilot.patchwork.block;

import java.util.List;

import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.dao.constant.BlockConstDao;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.struct.strategy.StructureConfig;
import ru.pilot.patchwork.service.struct.strategy.StructureParam;
import ru.pilot.patchwork.service.struct.strategy.WindowStructure;

public class WindowStructureTest {
    
    @Test
    public void windowStructureWithoutFrameConfig(){
        int blockCountX = 3;
        int blockCountY = 3;

        WindowStructure windowStructure = new WindowStructure();
        List<IBlock> templateList = new BlockConstDao().getTemplateList();

        BlockSet blockSet = windowStructure.fill(blockCountX, blockCountY, templateList, new StructureConfig());
        TextPrinterBlock.print(blockSet);
    }
    
    @Test
    public void windowStructureWithFrameConfig(){
        int blockCountX = 10;
        int blockCountY = 10;

        WindowStructure windowStructure = new WindowStructure();
        List<IBlock> templateList = new BlockConstDao().getTemplateList();

        StructureConfig config = new StructureConfig();
        config.addParam(StructureParam.VERTICAL_DELIMITER, 30);
        config.addParam(StructureParam.HORIZONTAL_DELIMITER, 30);
        BlockSet blockSet = windowStructure.fill(blockCountX, blockCountY, templateList, config);
        TextPrinterBlock.print(blockSet);
    }
    
}
