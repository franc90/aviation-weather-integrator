package pl.edu.agh.awi.scheduled.helper.flight;

import pl.edu.agh.awi.downloader.exceptions.FlightTaskException;

public abstract class SingleValueDownloaderHelper<T,V> {

    public T download(V key) {
        T val = getFromRepository(key);

        if (val == null) {
            throw new FlightTaskException("Could not find value for key: " + key);
        }

        return val;

    }

    protected abstract T getFromRepository(V key);

}
