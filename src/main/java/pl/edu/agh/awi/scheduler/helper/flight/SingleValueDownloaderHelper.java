package pl.edu.agh.awi.scheduler.helper.flight;

import pl.edu.agh.awi.scheduler.exception.SchedulerException;

public abstract class SingleValueDownloaderHelper<T, V> {

    public T download(V key) {
        T val = getFromRepository(key);

        if (val == null) {
            throw new SchedulerException("Could not find value for key: " + key);
        }

        return val;
    }

    protected abstract T getFromRepository(V key);

}
