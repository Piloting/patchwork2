package ru.pilot.patchwork.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ru.pilot.patchwork.dao.DaoFactory;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.paint.strategy.PaintStrategy;
import ru.pilot.patchwork.service.paint.strategy.RandomColor;
import ru.pilot.patchwork.service.struct.strategy.StructureStrategy;
import ru.pilot.patchwork.service.struct.strategy.WindowStructure;

public class PatchworkModel {
    
    // модель текущего проекта
    private BlockSet currentModel = new BlockSet();
    
    // доступные блоки для модели
    private TemplateBlocks templateBlocks;
    
    // генератор структуры и цветов 
    private StructureStrategy structureStrategy;
    private PaintStrategy paintStrategy;
    
    
    public PatchworkModel(){
        templateBlocks = new TemplateBlocks(DaoFactory.INSTANCE.getBlockDao().getTemplateList());
        paintStrategy = new RandomColor();
        structureStrategy = new WindowStructure();
    }
    
    /** Блоки для модели */
    private static class TemplateBlocks {
        private final List<IBlock> allBockList;
        private final Set<Long> activeBlockIds;
        public TemplateBlocks(List<IBlock> blockList){
            allBockList = blockList;
            activeBlockIds = blockList.stream().map(IBlock::getId).collect(Collectors.toSet());
        }
        public List<IBlock> getActiveBlocks(){
            return allBockList.stream().filter(b -> activeBlockIds.contains(b.getId())).collect(Collectors.toList());
        }
        public void changeActiveState(Long id){
            if (activeBlockIds.contains(id)){
                activeBlockIds.remove(id);
            } else {
                activeBlockIds.add(id);
            }
        }
    }
    
}

