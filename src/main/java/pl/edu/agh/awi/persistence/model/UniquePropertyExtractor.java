package pl.edu.agh.awi.persistence.model;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UniquePropertyExtractor {

    public static Map<String, Object> extractUniqueProperties(Object node) {
        return Arrays.stream(FieldUtils.getFieldsWithAnnotation(node.getClass(), Unique.class))
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

    private UniquePropertyExtractor(){}

}
