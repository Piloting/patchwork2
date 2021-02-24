package ru.pilot.patchwork.service.struct.strategy;

import java.util.HashMap;
import java.util.Map;

public class StructureConfig {
    private final Map<StructureParam, Object> paramMap = new HashMap<>();
    
    public <T> void addParam(StructureParam name, T value){
        paramMap.put(name, value);
    }
    
    public <T> T getParam(StructureParam name){
        Object o = paramMap.get(name);
        return (T) o;
    }
}
