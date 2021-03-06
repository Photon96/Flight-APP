package com.example.flightapp.JsonHandlers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class CustomJsonDateDeserializer extends JsonDeserializer<DateTime>
{
    @Override
    public DateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext) throws IOException {

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss Z");
        String date = jsonParser.getText();
        return dtf.parseDateTime(date);

    }
}
