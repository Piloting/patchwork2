package ru.pilot.patchwork.service.paint.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.image.PixelReader;
import org.apache.commons.collections4.CollectionUtils;
import ru.pilot.patchwork.model.ModelConfig;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.block.PolygonBlock;
import ru.pilot.patchwork.service.coord.BlockPointManipulatorFactory;
import ru.pilot.patchwork.service.coord.CoordUtils;
import ru.pilot.patchwork.service.coord.Point;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.picture.PictureUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static ru.pilot.patchwork.model.ModelParam.PICTURE;

public class PictureColor extends PaintStrategy {
    
    
    @Override
    public void fill(PolygonBlock polygonBlock, ModelConfig modelConfig) {
        throw new NotImplementedException();
    }

    public void fill(IBlock block, ModelConfig modelConfig){
        String imageName = modelConfig.getParam(PICTURE);
        if (imageName == null){
            throw new RuntimeException("Undefined image");
        }

        // надо определить размеры блоков, что бы накладывать на картинку размер в размер.
        List<PolygonBlock> polygonBlocks = block.getPolygonBlocks();
        Point size = CoordUtils.getSize(polygonBlocks);
        double blockWidth = size.getX();
        double blockHeight = size.getY();
        
        // координаты блоков должны начинаться от начала координат, как и картинка
        Point min = CoordUtils.getMin(polygonBlocks);
        if (min.getX() < -1 || min.getX() > 1 || min.getY() < -1 || min.getY() > 1){
            // нужно подвинуть блоки в начало координат, а потом вернуть обратно
            BlockPointManipulatorFactory.INSTANCE.getManipulator().translate(polygonBlocks, -min.getX(), -min.getY());
        }
        
        PixelReader pixelReader = PictureUtils.getFitImage(imageName, blockWidth, blockHeight).getPixelReader();
        Map<Long, List<ColorFill>> idToPixelColorsMap = new HashMap<>();
        // цикл по всем пикселям
        for (int w = 0; w <= blockWidth; w++) {
            for (int h = 0; h <= blockHeight; h++) {
                ColorFill color = PictureUtils.getColor(pixelReader, w, h);
                for (PolygonBlock polygonBlock : polygonBlocks) {
                    // проверяем в какой блок попадает пиксель
                    boolean intersect = BlockPointManipulatorFactory.INSTANCE.getManipulator().isIntersect(polygonBlock, new PolygonBlock(new double[]{w, h}, null));
                    if (intersect){
                        // сохраняем цвет
                        idToPixelColorsMap.putIfAbsent(polygonBlock.getId(), new ArrayList<>((int) (blockHeight * blockWidth / 16)));
                        idToPixelColorsMap.get(polygonBlock.getId()).add(color);
                    }
                }
            }
        }

        // расчет и установка средних цветов 
        Map<Long, PolygonBlock> polygonBlockById = createPolygonBlockMap(polygonBlocks);
        for (Map.Entry<Long, List<ColorFill>> entry : idToPixelColorsMap.entrySet()) {
            Long id = entry.getKey();
            PolygonBlock polygonBlock = polygonBlockById.get(id);
            if (polygonBlock != null){
                ColorFill avgColor = getAvgColor(entry.getValue());
                polygonBlock.setPaint(avgColor);
            }
        }

        // нужно вернуть блоки обратно
        if (min.getX() < -1 || min.getX() > 1 || min.getY() < -1 || min.getY() > 1){
            BlockPointManipulatorFactory.INSTANCE.getManipulator().translate(polygonBlocks, min.getX(), min.getY());
        }
    }

    private Map<Long, PolygonBlock> createPolygonBlockMap(List<PolygonBlock> polygonBlocks) {
        return polygonBlocks.stream().collect(Collectors.toMap(PolygonBlock::getId, item -> item));
    }

    private ColorFill getAvgColor(List<ColorFill> colorList) {
        if (CollectionUtils.isEmpty(colorList)){
            return ColorFill.BLACK;
        }
        
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        
        for (ColorFill color : colorList) {
            redSum += color.getRed();
            greenSum += color.getGreen();
            blueSum += color.getBlue();
        }
        
        int pixelCount = colorList.size();
        return new ColorFill(
                redSum/pixelCount,
                greenSum/pixelCount,
                blueSum/pixelCount
        );
    }
}
