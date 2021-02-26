package ru.pilot.patchwork.controller;

import java.util.List;

import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.ImageFill;

public interface LibraryController {
    
    //  Полотна
    //      текущее
    BlockSet getLastModel();
    //		примеры
    List<BlockSet> getSimpleModels();
    //		сохранения
    List<BlockSet> getLikeModels();
    
    //	Блоки (просмотр/редактирование/создание)
    List<IBlock> getTemplates();

    //	Заливка
    //		Ткани
    List<ImageFill> getPaints();
    //		Наборы Цветов (задать в ручную, по картинке N цветов)
    List<ColorFill> getColors();
}
