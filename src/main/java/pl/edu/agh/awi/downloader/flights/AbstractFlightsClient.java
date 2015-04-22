package pl.edu.agh.awi.downloader.flights;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractFlightsClient<T, U> {

    private Class<U> responseType;

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public AbstractFlightsClient(Class<U> responseType) {
        this.responseType = responseType;
    }

    protected U load() {
        try {
            return objectMapper.readValue(new URL(getUrl()), responseType);
        } catch (IOException e) {
            throw new MalformedUrlException("Error while getting response", e);
        }
    }

    public abstract T getResponse();

    protected abstract String getUrl();

}
