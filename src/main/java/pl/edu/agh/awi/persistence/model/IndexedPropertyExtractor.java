package pl.edu.agh.awi.persistence.model;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.neo4j.annotation.Indexed;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class IndexedPropertyExtractor {

    public static Map<String, Object> extractIndexedProperties(Object node) {
        return Arrays.stream(FieldUtils.getFieldsWithAnnotation(node.getClass(), Indexed.class))
                .filter(field -> getValue(node, field) != null)
                .collect(
                        Collectors.toMap(Field::getName, (Field field) -> getValue(node, field)));

    }

    private static Object getValue(Object node, Field field)   {
        try {
            field.setAccessible(true);
            return field.get(node);
        } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
        }
    }

    private IndexedPropertyExtractor(){}

}
