package ru.pilot.patchwork.dao;

import lombok.Getter;
import ru.pilot.patchwork.dao.mem.BlockMemoryDao;
import ru.pilot.patchwork.dao.mem.PaintMemoryDao;

@Getter
public class DaoFactory {
    public static final DaoFactory INSTANCE = new DaoFactory();
    
    private final BlockDao blockDao;
    private final PaintDao paintDao;
    
    private DaoFactory(){
        this(new BlockMemoryDao(), new PaintMemoryDao());
    }
    
    private DaoFactory(BlockDao blockDao, PaintDao paintDao){
        this.blockDao = blockDao;
        this.paintDao = paintDao;
    }
}
