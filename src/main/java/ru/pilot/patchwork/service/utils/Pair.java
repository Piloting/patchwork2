package ru.pilot.patchwork.service.utils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<K,V> implements Serializable{
    
    private K key;
    private V value;
    
    @Override
    public String toString() {
        return key + "=" + value;
    }
}
