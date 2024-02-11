package com.nstu.lab1.dataStructures;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ListDeserializer<T> extends JsonDeserializer<MyList<?>> {
    private JavaType valueType;

    public ListDeserializer(JavaType valueType) {
        this.valueType = valueType;
    }

    @Override
    public MyList<T> deserialize(JsonParser jsonParser, DeserializationContext context) 
            throws IOException, JsonProcessingException {
        MyList<T> list = new MyList<>();
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode nodes = oc.readTree(jsonParser);
        if (nodes.isArray()) {
            for (JsonNode node : nodes) {
                T value = (T) oc.treeToValue(node, valueType.getRawClass());
                list.add(value);
            }
        }
        return list;
    }
}
