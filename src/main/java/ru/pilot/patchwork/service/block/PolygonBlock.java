package ru.pilot.patchwork.service.block;


import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.Paint;

@Data
public class PolygonBlock implements IBlock {
    
    @JsonIgnore
    private final long id = BlockIdGenerator.getId();
    
    @JsonProperty(value = "paint")
    private Paint paint;

    @JsonProperty(value = "points")
    private double[] points;

    /** Признак похожести блока */
    @JsonProperty(value = "similarId")
    private Long similarId;

    public PolygonBlock(double[] points, Paint paint){
        this.points = points;
        this.paint = paint;
    }
    
    public PolygonBlock(){
        this.paint = ColorFill.GREEN;
        this.points = PolygonType.RECT.getSimplePoints();
    }
    
    public PolygonBlock(PolygonType polygonType){
        this.paint = ColorFill.GREEN;
        this.points = polygonType.getSimplePoints();
    }

    @JsonIgnore
    @Override
    public List<PolygonBlock> getPolygonBlocks() {
        return Collections.singletonList(this);
    }

    @Override
    public IBlock copyToNew() {
        return new PolygonBlock(points, paint);
    }
}
