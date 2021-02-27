package ru.pilot.patchwork.dao;

import java.util.List;

import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;

public interface PaintDao {
    // наборы
    List<PaintSet> getPaintSetList();
    void savePaintSet(PaintSet paintSet);
    void removePaintSet(Long id);

    // ткани/цвета
    List<Paint> getPaintList();
    void savePaint(Paint paint);
    void removePaint(Long id);
}
