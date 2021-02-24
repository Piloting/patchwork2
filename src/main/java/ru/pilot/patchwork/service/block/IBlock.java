package ru.pilot.patchwork.service.block;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.Point;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Block.class, name = "block"),
        @JsonSubTypes.Type(value = BlockSet.class, name = "blockSet") })
public interface IBlock {
    List<PolygonBlock> getPolygonBlocks();
    IBlock copyToNew();

    /** Центральная точка относительно всех полигонов */
    @JsonIgnore
    default Point getCenter(){
        return CoordUtils.getCenter(getPolygonBlocks());
    }
}
