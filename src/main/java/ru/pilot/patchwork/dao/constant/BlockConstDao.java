package ru.pilot.patchwork.dao.constant;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.apache.commons.collections4.list.UnmodifiableList;
import ru.pilot.patchwork.dao.BlockDao;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;


public class BlockConstDao implements BlockDao {
    private final List<IBlock> constBlockList;

    public BlockConstDao(){
        List<IBlock> blockList = new ArrayList<>(3);
        
        Block twoTriangle = new Block();
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,    0,10,   10,0}, null));
        twoTriangle.getPolygonBlocks().add(new PolygonBlock(new double[] {10,10,  10,0,   0,10}, null));
        blockList.add(twoTriangle);

        Block twoTriangle2 = new Block();
        twoTriangle2.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,    10,0,   10,10}, null));
        twoTriangle2.getPolygonBlocks().add(new PolygonBlock(new double[] {10,10,  0,10,   0,0}, null));
        blockList.add(twoTriangle2);

        Block rect = new Block();
        rect.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,   10,0,   10,10,   0,10}, null));
        blockList.add(rect);

        constBlockList = new UnmodifiableList<>(blockList);
    }


    @Override
    public List<IBlock> getTemplateList() {
        return constBlockList;
    }
}
