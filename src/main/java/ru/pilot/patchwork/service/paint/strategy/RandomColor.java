package ru.pilot.patchwork.service.paint.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;

public class RandomColor implements PaintStrategy {
    
    private static final Random rnd = new Random();
    protected PaintSet paintSet;
    
    public RandomColor(){
        
    }
    
    public RandomColor(int colorCount){
        this.paintSet = getColorSet(colorCount);
    }
    
    public RandomColor(PaintSet paintSet){
        this.paintSet = paintSet;
    }

    /** Раскрасим подобные блоки одинаково */
    @Override
    public void fill(IBlock block) {
        // сделаем цвета для одинаковых блоков
        Map<Long, Paint> similarMap = new HashMap<>();
        for (PolygonBlock polygonBlock : block.getPolygonBlocks()) {
            similarMap.putIfAbsent(polygonBlock.getSimilarId(), getRndPaint());
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
    public void fill(PolygonBlock polygonBlock) {
        polygonBlock.setPaint(getRndPaint());
    }

    private Paint getRndPaint(){
        return CollectionUtils.isEmpty(paintSet.getPaints()) ? getRgb() : paintSet.getRandomPaint();
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
