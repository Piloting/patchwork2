package ru.pilot.patchwork.model;

import java.util.HashMap;
import java.util.Map;

public class ModelConfig {
    private final Map<ModelParam, Object> paramMap = new HashMap<>();
    
    public <T> void addParam(ModelParam name, T value){
        paramMap.put(name, value);
    }
    
    public <T> T getParam(ModelParam name){
        Object o = paramMap.get(name);
        return (T) o;
    }
    
    public <T> T getParam(ModelParam name, T defaultValue){
        T value = getParam(name);
        return  value != null ? value : defaultValue;
    }
}
