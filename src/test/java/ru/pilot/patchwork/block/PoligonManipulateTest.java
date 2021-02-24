package ru.pilot.patchwork.block;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import ru.pilot.patchwork.ext.awt.TextPrinterBlock;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.block.PolygonType;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;
import ru.pilot.patchwork.service.coord.BlockPointManipulator;
import ru.pilot.patchwork.service.coord.MirrorType;
import ru.pilot.patchwork.service.paint.ColorFill;

public class PoligonManipulateTest {

    @Test
    public void manipulateMirror() {
        mirror(this::createSingleBlock);
        mirror(this::createMiltyBlock);
    }
    
    @Test
    public void manipulateRotate() {
        rotate(this::createSingleBlock);
        rotate(this::createMiltyBlock);
    }
    
    @Test
    public void manipulateTranslate() {
        translate(this::createSingleBlock);
        translate(this::createMiltyBlock);
    }
    
    @Test
    public void manipulateScale() {
        scale(this::createSingleBlock);
        scale(this::createMiltyBlock);
    }
    
    @Test
    public void manipulateCombo() {
        Block block = createMiltyBlock();
        print(block);

        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        manipulator.translate(block, 20, 20);
        manipulator.mirror(block, MirrorType.VERTICAL);
        manipulator.scale(block, 2,2);
        manipulator.rotation(block, 45);
        print(block);
    }


    private void scale(Supplier<Block> blockSupplier) {
        Block block = blockSupplier.get();
        print(block);

        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        manipulator.scale(block, 2, 1);
        print(block);

        block = blockSupplier.get();
        manipulator.scale(block, 1, 2);
        print(block);

        block = blockSupplier.get();
        manipulator.scale(block, 2, 2);
        print(block);
    }

    private void translate(Supplier<Block> blockSupplier) {
        Block block = blockSupplier.get();
        print(block);

        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        manipulator.translate(block, 10, 0);
        print(block);

        block = blockSupplier.get();
        manipulator.translate(block, 0, 10);
        print(block);

        block = blockSupplier.get();
        manipulator.translate(block, 10, 10);
        print(block);

    }

    private void rotate(Supplier<Block> blockSupplier) {
        Block block = blockSupplier.get();
        print(block);

        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        manipulator.rotation(block, 45);
        print(block);

        block = blockSupplier.get();
        manipulator.rotation(block, 90);
        print(block);
        
        block = blockSupplier.get();
        manipulator.rotation(block, 180);
        print(block);
        
        block = blockSupplier.get();
        manipulator.rotation(block, 0);
        print(block);
    }

    private void mirror(Supplier<Block> blockSupplier) {
        Block block = blockSupplier.get();
        print(block);
        
        BlockPointManipulator manipulator = BlockPointManipulatorFactory.INSTANCE.getManipulator();
        manipulator.mirror(block, MirrorType.HORIZONTAL);
        print(block);

        block = blockSupplier.get();
        manipulator.mirror(block, MirrorType.VERTICAL);
        print(block);

        block = blockSupplier.get();
        manipulator.mirror(block, MirrorType.DIAGONAL);
        print(block);
    }

    private Block createSingleBlock() {
        Block block = new Block();
        block.getPolygonBlocks().add(new PolygonBlock(PolygonType.TRIANGLE.getSimplePoints(), ColorFill.BLUE));
        return block;
    }
    
    private Block createMiltyBlock() {
        Block block = new Block();
        block.getPolygonBlocks().add(new PolygonBlock(new double[] {0,0,    0,10,   10,0        }, ColorFill.BLUE));
        block.getPolygonBlocks().add(new PolygonBlock(new double[] {10,10,  10,0,   0,10        }, ColorFill.BLUE));
        block.getPolygonBlocks().add(new PolygonBlock(new double[] {10,0,   10,10,  20,10,  20,0}, ColorFill.BLUE));
        return block;
    }
    
    private void print(IBlock iBlock){
        TextPrinterBlock.print(iBlock);
    }

}
