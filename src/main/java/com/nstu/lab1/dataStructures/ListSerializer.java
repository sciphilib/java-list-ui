package com.nstu.lab1.dataStructures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ListSerializer<T> extends StdSerializer<MyList<?>> {

    public ListSerializer() {
        super(MyList.class, false);
    }

    @Override
    public void serialize(MyList<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("elements");
        gen.writeStartArray();
        for (Object element : value.toList()) {
            gen.writeObject(element);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
