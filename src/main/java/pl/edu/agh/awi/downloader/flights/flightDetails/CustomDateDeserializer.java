package pl.edu.agh.awi.downloader.flights.flightDetails;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String text = p.getText();
        if (text == null || text.isEmpty()) {
            return null;
        }

        Long value = Long.valueOf(text);
        long milliseconds = value * 1000L;
        return new Date(milliseconds);
    }
}
