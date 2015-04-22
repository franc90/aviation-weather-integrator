package pl.edu.agh.awi.persistence.model;

import org.springframework.data.neo4j.annotation.Indexed;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
@Indexed
public @interface Unique {
}
