package pl.edu.agh.awi.loader.converter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface LoaderConverter<T, U> extends Function<T, U> {

    default List<U> convert(List<T> list) {
        List<U> airLines = list.stream().map(this::apply).collect(Collectors.<U>toList());
        return airLines;
    }

}
