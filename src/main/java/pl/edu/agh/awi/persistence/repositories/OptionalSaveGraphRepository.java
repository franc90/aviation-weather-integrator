package pl.edu.agh.awi.persistence.repositories;


import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.model.UniquePropertyExtractor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoRepositoryBean
public interface OptionalSaveGraphRepository<T> extends GraphRepository<T> {

    @Transactional
    default Iterable<T> saveIfNotExists(Collection<T> nodes) {
        return nodes.stream()
                    .map(this::saveIfNotExists)
                    .collect(Collectors.toList());
    }

    @Transactional
    default T saveIfNotExists(T node) {
        return load(node).orElse(save(node));
    }

    default Optional<T> load(T node) {
        return UniquePropertyExtractor.extractUniqueProperties(node).entrySet()
                .stream()
                .map(entry -> findAllBySchemaPropertyValue(entry.getKey(), entry.getValue()))
                .map(Result::iterator)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .filter(Objects::nonNull)
                .findAny();
    }
}
