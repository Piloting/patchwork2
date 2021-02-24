package ru.pilot.patchwork.service.paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class PaintSet {

    private static final Random rnd = new Random();
    private final List<Paint> paints;
    
    public PaintSet(){
        this(new ArrayList<>());
    }
    
    public PaintSet(List<Paint> paints){
        this.paints = paints;
    }
    
    public Paint getRandomPaint(){
        return paints.isEmpty() ? null : paints.get(rnd.nextInt(paints.size()));
    }
    
}
