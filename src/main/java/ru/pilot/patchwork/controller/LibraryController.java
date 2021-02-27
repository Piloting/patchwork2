package ru.pilot.patchwork.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.pilot.patchwork.dao.DaoFactory;
import ru.pilot.patchwork.model.ModelService;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.paint.ImageFill;
import ru.pilot.patchwork.service.paint.Paint;
import ru.pilot.patchwork.service.paint.PaintSet;

public class LibraryController {

    // текущее Полотно
    public BlockSet getLastModel(){
        return DaoFactory.INSTANCE.getBlockDao().getCurrentBlockSet();
    }
    public void saveLastModel(BlockSet blockSet){
        DaoFactory.INSTANCE.getBlockDao().setCurrentBlockSet(blockSet);
    }


    //		примеры
    public List<BlockSet> getSimpleModels(Long paintSetId){
        Optional<PaintSet> paintSet = getPaintSet().stream().filter(ps -> ps.getId().equals(paintSetId)).findFirst();
        return new ModelService().generateVariousSimples(paintSet.orElse(null));
    }


    //		сохранения
    public List<BlockSet> getLikeModels(){
        return DaoFactory.INSTANCE.getBlockDao().getLikeModels();
    }
    public void saveLikeModel(BlockSet blockSet){
        DaoFactory.INSTANCE.getBlockDao().saveLikeModel(blockSet);
    }


    //	Блоки (просмотр todo редактирование/создание)
    public List<IBlock> getTemplates(){
        return DaoFactory.INSTANCE.getBlockDao().getTemplateList();
    }

    //	Заливка
    //		Ткани
    public List<ImageFill> getCloth(){
        List<Paint> paintList = DaoFactory.INSTANCE.getPaintDao().getPaintList();
        return paintList.stream()
                .filter(paint -> paint instanceof ImageFill)
                .map(paint -> (ImageFill) paint)
                .collect(Collectors.toList());
    }
    public void saveCloth(ImageFill imageFill){
        DaoFactory.INSTANCE.getPaintDao().savePaint(imageFill);
    }
    public void removeCloth(Long id){
        DaoFactory.INSTANCE.getPaintDao().removePaint(id);
    }


    //		Наборы Цветов (задать в ручную, по картинке N цветов)
    public List<PaintSet> getPaintSet(){
        return DaoFactory.INSTANCE.getPaintDao().getPaintSetList();
    }
    public void savePaintSet(PaintSet paintSet){
        DaoFactory.INSTANCE.getPaintDao().savePaintSet(paintSet);
    }
    public void removePaintSet(Long id){
        DaoFactory.INSTANCE.getPaintDao().removePaintSet(id);
    }
}
