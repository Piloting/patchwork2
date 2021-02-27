package ru.pilot.patchwork.service.block;

public class BlockIdGenerator {
    private static volatile long id = 0L;

    public static synchronized long getId() {
        return id++;
    }
}
