package pl.edu.agh.awi.scheduled.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:config/hazelcast-config.xml")
public class CacheConfig {


}
