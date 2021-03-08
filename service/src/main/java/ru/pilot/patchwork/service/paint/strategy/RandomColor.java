package ru.pilot.patchwork.service.paint.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.model.ModelParam;
import ru.pilot.patchwork.service.block.Block;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;

public class RandomColor extends PaintStrategy {
    
    private static final Random rnd = new Random();
    protected PaintSet paintSet;
    
    public RandomColor(){
        this(5);
    }
    
    public RandomColor(int colorCount){
        this.paintSet = getColorSet(colorCount);
    }
    
    public RandomColor(PaintSet paintSet){
        this.paintSet = paintSet != null ? paintSet : getColorSet(5);
    }

    /** Раскрасим подобные блоки одинаково */
    @Override
    public void fill(IBlock block, ModelConfig modelConfig) {
        PaintSet paintSetConfig = modelConfig.getParam(ModelParam.PAINT_SET);
        if (paintSetConfig != null){
            paintSet = paintSetConfig;
        }

        // сделаем цвета для одинаковых блоков
        Map<Long, Paint> similarMap = new HashMap<>();
        
        if (block instanceof BlockSet){
            for (Block innerBlock : ((BlockSet) block).getBlocks()) {
                // в пределах блока желательно делать разные цвета 
                List<Paint> existPaints = new ArrayList<>();
                for (PolygonBlock polygonBlock : innerBlock.getPolygonBlocks()) {
                    Paint uniqueColor = getUniqueColor(existPaints);
                    existPaints.add(uniqueColor);
                    similarMap.putIfAbsent(polygonBlock.getSimilarId(), uniqueColor);
                }
            }
        }

        // заполним цвета
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            if (polygonBlock.getSimilarId() != null){
                polygonBlock.setPaint(similarMap.get(polygonBlock.getSimilarId()));
            } else {
                polygonBlock.setPaint(getRndPaint());
            }
        }
    }
    
    @Override
    public void fill(PolygonBlock polygonBlock, ModelConfig modelConfig) {
        polygonBlock.setPaint(getRndPaint());
    }

    private Paint getRndPaint(){
        return CollectionUtils.isEmpty(paintSet.getPaints()) ? getRgb() : paintSet.getRandomPaint();
    }

    private Paint getUniqueColor(Collection<Paint> existsColor){
        int i = 0;
        Paint color;
        do {
            color = getRndPaint();
            i++;
        } while (i<10 && existsColor.contains(color));
        return color;
    }

    private PaintSet getColorSet(int colorCount) {
        List<Paint> list = new ArrayList<>(colorCount);
        for (int i = 0; i < colorCount; i++) {
            list.add(getRgb());
        }
        return new PaintSet(list);
    }

    protected ColorFill getRgb() {
        return new ColorFill(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }

}
