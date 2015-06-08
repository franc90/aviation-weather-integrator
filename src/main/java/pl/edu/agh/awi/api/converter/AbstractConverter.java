package pl.edu.agh.awi.api.converter;

import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractConverter<T, U> {

    public U convert(Optional<T> source) {
        if (source.isPresent()) {
            return getConverted(source.get());
        }
        return null;
    }

    public abstract U deepConvert(Optional<T> source);

    public abstract U getConverted(T source);

    public Set<U> deepConvert(Set<T> source) {
        return setConvert(source, this::deepConvert);
    }

    public Set<U> convert(Set<T> source) {
        return setConvert(source, this::convert);
    }

    public Set<U> setConvert(Set<T> source, Function<Optional<T>, U> convertFk) {
        if (CollectionUtils.isEmpty(source)) {
            return null;
        }

        return source
                .stream()
                .map(Optional::ofNullable)
                .map(convertFk::apply)
                .filter(e -> e != null)
                .collect(Collectors.toSet());
    }


}
