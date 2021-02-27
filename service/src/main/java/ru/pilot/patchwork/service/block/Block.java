package ru.pilot.patchwork.service.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Блок в пейчворке - сшитые в блок лоскуты ткани (обычно квадрат) 
 * 
 * Блок состоит из нескольких отдельных лоскутов (полигон)
 * Каждый лоскут имеет свои координаты, они должны быть указаны относительно начала координат блока (левый верхний угол)
 */
public class Block implements IBlock {
    
    @Getter
    @JsonProperty(value = "polygonBlocks")
    private final List<PolygonBlock> polygonBlocks = new ArrayList<>();
    
    @JsonIgnore
    private final long id = BlockIdGenerator.getId();
    public Long getId(){
        return id;
    }

    public Block copyToNew(){
        Block newBlock = new Block();
        for (PolygonBlock polygonBlock : getPolygonBlocks()) {
            double[] initPoints = polygonBlock.getPoints();
            PolygonBlock newPolygonBlock = new PolygonBlock(Arrays.copyOf(initPoints, initPoints.length), polygonBlock.getPaint(), polygonBlock.getSimilarId());
            newBlock.getPolygonBlocks().add(newPolygonBlock);
        }
        return newBlock;
    }
    
}
