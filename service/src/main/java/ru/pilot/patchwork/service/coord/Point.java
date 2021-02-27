package ru.pilot.patchwork.service.coord;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point implements Serializable{
    private double x;
    private double y;
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
