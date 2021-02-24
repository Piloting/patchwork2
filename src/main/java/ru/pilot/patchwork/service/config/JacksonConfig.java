package ru.pilot.patchwork.service.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.pilot.patchwork.service.paint.ColorFill;
import ru.pilot.patchwork.service.paint.Paint;


public class JacksonConfig {
    
    public ObjectMapper objectMapper() {
        return createObjectMapper();
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(Paint.class, new PaintSerializer());
        module.addDeserializer(Paint.class, new PaintDeserializer());
        mapper.registerModule(module);
        
        return mapper;
    }
    
    public static class PaintSerializer extends StdSerializer<Paint> {
        public PaintSerializer() {
            this(null);
        }
        public PaintSerializer(Class<Paint> t) {
            super(t);
        }

        @Override
        public void serialize(Paint value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartObject();
            
            // todo ImageFill
            
            ColorFill color = (ColorFill) value;
            jgen.writeNumberField("red", color.getRed());
            jgen.writeNumberField("green", color.getGreen());
            jgen.writeNumberField("blue", color.getBlue());
            jgen.writeEndObject();
        }
    }
    
    public static class PaintDeserializer extends StdDeserializer<Paint> {
        public PaintDeserializer() {
            this(null);
        }
        public PaintDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public ColorFill deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode node = jp.getCodec().readTree(jp);
            int red = node.get("red").intValue();
            int green = node.get("green").intValue();
            int blue = node.get("blue").intValue();
            return new ColorFill(red, green, blue);
        }
    }
}
