package pl.edu.agh.awi;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.edu.agh.awi.loader.AbstractLoader;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

    public static final String DEFAULT_ZONE = "northamerica";
    @Autowired
    private List<AbstractLoader> loaders;

    @Autowired
    private HazelcastInstance hazelcast;

    private IList<String> zones;

    @PostConstruct
    public void init() {
        zones = hazelcast.getList("zones");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        addZones(args);

        loaders.forEach(e -> e.loadData());
    }

    private void addZones(String... args) {
        if (args.length > 0) {
            for (String arg : args) {
                zones.add(arg);
            }
        } else {
            zones.add(DEFAULT_ZONE);
        }
    }
}
