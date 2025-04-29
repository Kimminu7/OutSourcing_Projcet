package org.example.outsourcing_project.common.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.outsourcing_project.common.enums.ShopDayOfWeek;

import java.io.IOException;

public class DayOfWeekDeserializer extends JsonDeserializer<ShopDayOfWeek> {

    @Override
    public ShopDayOfWeek deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        return ShopDayOfWeek.of(value);
    }
}
