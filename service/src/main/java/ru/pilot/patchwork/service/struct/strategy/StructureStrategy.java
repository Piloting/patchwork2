package ru.pilot.patchwork.service.struct.strategy;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.StructureStrategyType;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;

public abstract class StructureStrategy {
    public abstract BlockSet fill(int blockCountX, int blockCountY, List<IBlock> availableBlockList, ModelConfig config);
    protected abstract StructureStrategyType getType();
    
    protected void validate(int blockCountX, int blockCountY, List<IBlock> availableBlockList) {
        if (blockCountX < 1 || blockCountY < 1){
            throw new InvalidParameterException("Structure, bad param: blockCountX=" + blockCountX + ", blockCountY="+ blockCountY);
        }
        if (CollectionUtils.isEmpty(availableBlockList)){
            throw new InvalidParameterException("Structure, no available Block");
        }
    }
    
    public static StructureStrategy getImplByType(StructureStrategyType type){
        switch (type){
            case ChessStructure: return new ChessStructure();
            case LineStructure: return new LineStructure();
            case RandomStructure: return new RandomStructure();
            case WindowStructure: return new WindowStructure();
            default: throw new RuntimeException("Not implemented");
        }
    }
}
