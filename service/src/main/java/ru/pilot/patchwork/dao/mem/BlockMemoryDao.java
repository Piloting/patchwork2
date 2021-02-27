package ru.pilot.patchwork.dao.mem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.list.UnmodifiableList;
import ru.pilot.patchwork.dao.BlockDao;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;


public class BlockMemoryDao implements BlockDao {
    private final List<IBlock> constBlockList;
    private final Map<Long, BlockSet> idToBlockSetMap = new HashMap<>();
    private Long currentBlockSetId;
    private final Set<Long> savedBlockSetId = new HashSet<>();

    public BlockMemoryDao(){
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
    
    // текущее полотно 
    @Override
    public BlockSet getCurrentBlockSet() {
        return idToBlockSetMap.get(currentBlockSetId);
    }
    @Override
    public void setCurrentBlockSet(BlockSet currentBlockSet) {
        currentBlockSetId = currentBlockSet.getId();
        idToBlockSetMap.put(currentBlockSetId, currentBlockSet);
    }

    // сохранения
    @Override
    public List<BlockSet> getLikeModels(){
        return idToBlockSetMap.entrySet().stream()
                .filter((entry) -> savedBlockSetId.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    @Override
    public void saveLikeModel(BlockSet blockSet){
        savedBlockSetId.add(blockSet.getId());
        idToBlockSetMap.put(blockSet.getId(), blockSet);
    }
    @Override
    public void dislikeModel(Long id){
        savedBlockSetId.removeIf(item -> item.equals(id));
        idToBlockSetMap.keySet().remove(id);
    }
    
}
