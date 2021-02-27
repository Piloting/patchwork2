package ru.pilot.patchwork.service.block;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;

public class BlockSet implements IBlock {
    
    /**
     * Блоки
     * Т.е. координаты полигонов внутри блока отсчитываются от системы координат всего BlockSet
     */
    @Getter
    @JsonProperty(value = "blocks")
    private final List<IBlock> blocks = new ArrayList<>();

    @JsonIgnore
    private final long id = BlockIdGenerator.getId();
    public Long getId(){
        return id;
    }
    
    public BlockSet copyToNew(){
        BlockSet newBlockSet = new BlockSet();
        for (IBlock block : blocks) {
            newBlockSet.getBlocks().add(block.copyToNew());
        }
        return newBlockSet;
    }

    @Override
    public List<PolygonBlock> getPolygonBlocks() {
        List<PolygonBlock> polygonBlocks = new ArrayList<>();
        for (IBlock block : blocks) {
            polygonBlocks.addAll(block.getPolygonBlocks());
        }
        return polygonBlocks;
    }
    
    public void addBLock(IBlock block, double x, double y){
        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        manipulator.translate(block, x, y);
        blocks.add(block);
    }
}
