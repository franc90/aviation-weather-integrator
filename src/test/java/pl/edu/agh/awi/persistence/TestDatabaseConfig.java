package pl.edu.agh.awi.persistence;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@Profile("test")
public class TestDatabaseConfig extends DatabaseConfig {

    private static final String TEST_DB = "data/test.db";

    @Override
    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(TEST_DB);
    }

    @PostConstruct
    void init() {
        cleanDatabase();
    }

    @PreDestroy
    void destroy() {
        cleanDatabase();
    }

    private void cleanDatabase() {
        try {
            File db = new File(TEST_DB);
            if (db.exists())
                FileUtils.deleteRecursively(db);
        } catch (IOException ex) {
            Logger.getLogger(TestDatabaseConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
