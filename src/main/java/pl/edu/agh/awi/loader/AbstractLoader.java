package pl.edu.agh.awi.loader;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.loader.converter.LoaderConverter;

public abstract class AbstractLoader<T, U, V> {

    @Autowired
    protected AbstractFlightsClient<T, ?> client;

    @Autowired
    protected LoaderConverter<U, V> converter;

    public abstract void loadData();

}
