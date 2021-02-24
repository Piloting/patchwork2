package ru.pilot.patchwork.service.coord;

import lombok.Getter;

public enum MirrorType {
    HORIZONTAL(-1,1),
    VERTICAL(1,-1),
    DIAGONAL(-1,-1);
    
    @Getter
    int scaleX, scaleY;

    MirrorType(int scaleX, int scaleY){
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
}
