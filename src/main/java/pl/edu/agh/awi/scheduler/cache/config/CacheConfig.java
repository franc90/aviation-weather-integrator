package pl.edu.agh.awi.scheduler.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.scheduler.cache.CachedFlight;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class CacheConfig {

    @Bean
    public Map<String, CachedFlight> flights() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<String, CachedFlight> finishedFlights() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public List<Zone> zones() {
        return new CopyOnWriteArrayList<>();
    }
}
