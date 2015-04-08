package pl.edu.agh.awi.downloader.flights.flightDetails;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        Optional<String> text = Optional.ofNullable(parser.getText());

        return text.map(time -> {
            long seconds = Long.valueOf(time);
            long milliseconds = seconds * 1000L;
            return new Date(milliseconds);
        }).orElse(null);
    }
}
