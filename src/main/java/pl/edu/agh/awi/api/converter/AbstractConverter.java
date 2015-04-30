package pl.edu.agh.awi.api.converter;

import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractConverter<T, U> {

    public abstract U convert(T source, boolean deep);

    public Set<U> convert(Set<T> source, boolean deep) {
        if (CollectionUtils.isEmpty(source)) {
            return null;
        }

        return source
                .stream()
                .map(e -> convert(e, deep))
                .filter(e -> e != null)
                .collect(Collectors.toSet());
    }


}
