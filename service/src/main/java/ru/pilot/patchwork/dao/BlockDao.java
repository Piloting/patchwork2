package ru.pilot.patchwork.dao;

import java.util.List;

import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;

public interface BlockDao {
    
    List<IBlock> getTemplateList();

    BlockSet getCurrentBlockSet();

    void setCurrentBlockSet(BlockSet currentBlockSet);

    //		сохранения
    List<BlockSet> getLikeModels();

    void saveLikeModel(BlockSet blockSet);

    void dislikeModel(Long id);
}
