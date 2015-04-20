package pl.edu.agh.awi.loader.file_loader;

public interface FileLoader<T> {

    void clear();

    T getValue(String airportIcao);
}
