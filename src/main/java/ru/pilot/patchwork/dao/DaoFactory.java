package ru.pilot.patchwork.dao;

import ru.pilot.patchwork.dao.constant.BlockConstDao;

public class DaoFactory {
    public static final DaoFactory INSTANCE = new DaoFactory();

    private final BlockDao blockDao;
    
    private DaoFactory(){
        this(new BlockConstDao());
    }
    
    private DaoFactory(BlockDao blockDao){
        this.blockDao = blockDao;
    }
    
    public BlockDao getBlockDao(){
        return blockDao;
    }
    
}
