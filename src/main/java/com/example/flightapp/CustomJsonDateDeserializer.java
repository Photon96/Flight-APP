package com.example.flightapp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

public class CustomJsonDateDeserializer extends JsonDeserializer<DateTime>
{
    @Override
    public DateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext) throws IOException {

        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z", Locale.getDefault());
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss Z");

        String date = jsonParser.getText();
        System.out.println(dtf.parseDateTime(date).toString());
        return dtf.parseDateTime(date);
        /*try {
            //return format.parse(date);

        } catch () {
            throw new RuntimeException(e);
        }*/
    }
}
