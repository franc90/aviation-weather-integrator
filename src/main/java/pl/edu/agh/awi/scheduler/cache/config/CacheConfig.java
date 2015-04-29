package pl.edu.agh.awi.scheduler.cache.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.scheduler.cache.CachedFlight;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, CachedFlight> flights() {
        return CacheBuilder.newBuilder()
                .maximumSize(1500)
                .build();
    }

    @Bean
    public Cache<String, CachedFlight> finishedFlights() {
        return CacheBuilder.newBuilder()
                .maximumSize(1500)
                .build();
    }

    @Bean
    public List<Zone> zones() {
        return new CopyOnWriteArrayList<>();
    }
}
