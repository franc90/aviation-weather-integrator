package pl.edu.agh.awi.persistence;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Import(DatabaseConfig.class)
@EnableNeo4jRepositories(basePackages = PersistenceConfig.BASE_PACKAGE)
public class PersistenceConfig extends Neo4jConfiguration {

    public static final String BASE_PACKAGE = "pl.edu.agh.awi.persistence";

    public PersistenceConfig() {
        setBasePackage(BASE_PACKAGE);
    }

}
