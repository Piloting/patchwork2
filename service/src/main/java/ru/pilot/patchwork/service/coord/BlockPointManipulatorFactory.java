package ru.pilot.patchwork.service.coord;

import lombok.Getter;
import ru.pilot.patchwork.ext.javafx.BlockPointManipulatorFx;

public class BlockPointManipulatorFactory {
    public static final BlockPointManipulatorFactory INSTANCE = new BlockPointManipulatorFactory();
    
    @Getter
    private final BlockPointManipulator manipulator;
    
    private BlockPointManipulatorFactory(){
        manipulator = new BlockPointManipulatorFx();
    }
}
