package pl.edu.agh.awi.persistence;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@Configuration
public class DatabaseConfig {

    private static final String HOST = "http://178.62.52.247:7474/db/data";
    private static final String USER = "neo4j";
    private static final String PASSWORD = "Hard_pa$5";

    @Bean
    GraphDatabaseService graphDatabaseService() {
        return new SpringCypherRestGraphDatabase(HOST, USER, PASSWORD);
    }

}
