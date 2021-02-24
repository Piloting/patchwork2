package ru.pilot.patchwork.dao;

import java.util.List;

import ru.pilot.patchwork.service.block.IBlock;

public interface BlockDao {
    
    List<IBlock> getTemplateList();
    
}
