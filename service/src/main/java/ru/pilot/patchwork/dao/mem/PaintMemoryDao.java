package ru.pilot.patchwork.dao.mem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.pilot.patchwork.dao.PaintDao;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.ImageFill;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;

public class PaintMemoryDao implements PaintDao {
    private List<Paint> paintList = new ArrayList<>();
    private List<PaintSet> paintSetList = new ArrayList<>();
    
    public PaintMemoryDao(){
        // заготовки цветов с InColorBalance
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#3c1d25"), ColorFill.web("#68212f"), ColorFill.web("#a5395d"), ColorFill.web("#e088ae"), ColorFill.web("#cbe81e"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#c3547f"), ColorFill.web("#e087b5"), ColorFill.web("#97a7c8"), ColorFill.web("#b7adb6"), ColorFill.web("#edeffc"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#507160"), ColorFill.web("#d37a9a"), ColorFill.web("#ecb9e2"), ColorFill.web("#7d7fbc"), ColorFill.web("#7d8d9d"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#2f1f3c"), ColorFill.web("#523956"), ColorFill.web("#745567"), ColorFill.web("#b99595"), ColorFill.web("#dac1ba"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#111312"), ColorFill.web("#2c475c"), ColorFill.web("#6495b3"), ColorFill.web("#f4f9fc"), ColorFill.web("#997a66"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#ffffff"), ColorFill.web("#845f4d"), ColorFill.web("#bf2f6a"), ColorFill.web("#25559d"), ColorFill.web("#d8dd2b"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#514249"), ColorFill.web("#7f5a54"), ColorFill.web("#b2968a"), ColorFill.web("#c3c3c3"), ColorFill.web("#ffffff"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#883c63"), ColorFill.web("#cc5e7b"), ColorFill.web("#b98ea1"), ColorFill.web("#f4d9be"), ColorFill.web("#868c22"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#593366"), ColorFill.web("#a66aa8"), ColorFill.web("#e6c2dc"), ColorFill.web("#d9dbe7"), ColorFill.web("#acb01b"))));
        paintSetList.add(new PaintSet(Arrays.asList(ColorFill.web("#580201"), ColorFill.web("#ab0068"), ColorFill.web("#e00702"), ColorFill.web("#ff6c02"), ColorFill.web("#fec106"))));

        paintList.add(new ImageFill("cloth1.jpg"));
        paintList.add(new ImageFill("cloth2.jpg"));
        paintList.add(new ImageFill("cloth3.jpg"));
        paintList.add(new ImageFill("cloth4.jpg"));
        paintSetList.add(new PaintSet(paintList));
    }

    // наборы
    @Override
    public List<PaintSet> getPaintSetList(){
        return paintSetList;
    }
    @Override
    public void savePaintSet(PaintSet paintSet){
        paintSetList.add(paintSet);
    }
    @Override
    public void removePaintSet(Long id){
        paintSetList.removeIf(paintSet -> paintSet.getId().equals(id));
    }
    
    // ткани/цвета
    @Override
    public List<Paint> getPaintList(){
        return paintList;
    }
    @Override
    public void savePaint(Paint paint){
        paintList.add(paint);
    }
    @Override
    public void removePaint(Long id){
        paintList.removeIf(paint -> paint.getId().equals(id));
    }
    
}
