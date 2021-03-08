package ru.pilot.patchwork.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pilot.patchwork.service.block.BlockSet;
import ru.pilot.patchwork.service.block.IBlock;
import ru.pilot.patchwork.service.config.JacksonConfig;

public class YmlUtils {

    private static ObjectMapper objectMapper = null;
    private static final Logger logger = LoggerFactory.getLogger(YmlUtils.class);

    private static synchronized void init(){
        objectMapper = new JacksonConfig().objectMapper();
    }
    
    public static void setObjectMapper(ObjectMapper customObjectMapper) {
        objectMapper = customObjectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null){
            init();
        }
        return objectMapper;
    }
    
    @SneakyThrows
    public static String toYml(IBlock block){
        ObjectMapper objectMapper = getObjectMapper();
        String yml = objectMapper.writeValueAsString(block);
        logger.debug(yml);
        return yml;
    }
    
    @SneakyThrows
    public static IBlock fromYml(String yml){
        ObjectMapper objectMapper = getObjectMapper();
        try {
            return objectMapper.readValue(yml, BlockSet.class);
        } catch (Exception e){
            // empty
        }

        return objectMapper.readValue(yml, IBlock.class);
    }
    
}
