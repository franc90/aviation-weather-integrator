package pl.edu.agh.awi.persistence.repositories;


import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.agh.awi.persistence.model.IndexedPropertyExtractor;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface OptionalSaveGraphRepository<T> extends GraphRepository<T> {


    default Iterable<T> saveIfNotExists(Collection<T> nodes) {
        return nodes.stream()
                    .map(this::saveIfNotExists)
                    .collect(Collectors.toList());
    }

    default T saveIfNotExists(T node) {
        return load(node).orElse(save(node));
    }

    default Optional<T> load(T node) {
        return IndexedPropertyExtractor.extractIndexedProperties(node).entrySet()
                .stream()
                .map(entry -> findBySchemaPropertyValue(entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull)
                .findAny();
    }
}
