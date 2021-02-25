package ru.pilot.patchwork.service.struct.strategy;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;

public interface StructureStrategy {
    BlockSet fill(int blockCountX, int blockCountY, List<IBlock> availableBlockList, StructureConfig config);
    
    default void validate(int blockCountX, int blockCountY, List<IBlock> availableBlockList) {
        if (blockCountX < 1 || blockCountY < 1){
            throw new InvalidParameterException("Structure, bad param: blockCountX=" + blockCountX + ", blockCountY="+ blockCountY);
        }
        if (CollectionUtils.isEmpty(availableBlockList)){
            throw new InvalidParameterException("Structure, no available Block");
        }
    }
}