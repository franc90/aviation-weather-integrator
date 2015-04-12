package pl.edu.agh.awi.persistence.model;


import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.lang.FunctionalInterface;

public class ModelBuilder {

    @FunctionalInterface
    public interface Setter<T> extends Consumer<T> {}

    @SafeVarargs
    public static <T> T build(Supplier<T> modelSupplier, Setter<T>... setters) {
        final T model = modelSupplier.get();
        Stream.of(setters).forEach(s -> s.accept(model));
        return model;
    }

    private ModelBuilder(){}

}
