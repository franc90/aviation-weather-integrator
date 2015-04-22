package pl.edu.agh.awi.persistence.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.PersistenceConfig;
import pl.edu.agh.awi.persistence.TestDatabaseConfig;
import pl.edu.agh.awi.persistence.model.LoadBalancer;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
@Transactional
public class LoadBalancerRepositoryTest {

    @Autowired
    private LoadBalancerRepository repository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;


    @Test
    public void shouldNotSaveZone() {
        LoadBalancer givenBalancer = createLoadBalancer(1);
        givenBalancer = neo4jTemplate.save(givenBalancer);
        LoadBalancer balancer = repository.saveIfNotExists(givenBalancer);
        assertNotNull(balancer);
        assertEquals(givenBalancer, balancer);
    }

    @Test
    public void shouldSaveZone() {
        LoadBalancer givenBalancer = createLoadBalancer(1);
        LoadBalancer balancer = repository.saveIfNotExists(givenBalancer);
        assertNotNull(balancer);
        assertNotNull(balancer.getId());
    }

    @Test
    public void shouldSaveLoadBalancers() {
        int expectedCount = 10;
        createAndSaveBalancers(expectedCount);
        assertExpectedCount(expectedCount);
    }

    private void assertExpectedCount(int expectedCount) {
        assertTrue(neo4jTemplate.count(LoadBalancer.class) == expectedCount);
        Collection<LoadBalancer> loadBalancersFromDb = repository.findAll().as(Collection.class);
        assertTrue(loadBalancersFromDb.size() == expectedCount);
    }

    @Test
    public void shouldRemoveAllBalancers() {
        int numberOfBalancersToCreate = 10;
        int expectedCount = 0;
        createAndSaveBalancers(numberOfBalancersToCreate);
        repository.deleteAll();
        assertExpectedCount(expectedCount);
    }

    private void createAndSaveBalancers(int expectedCount) {
        Collection<LoadBalancer> loadBalancers = createLoadBalancers(expectedCount);
        repository.save(loadBalancers);
    }

    private Collection<LoadBalancer> createLoadBalancers(int count) {
        return IntStream.range(0, count)
                .mapToObj(this::createLoadBalancer)
                .collect(Collectors.toList());
    }

    private LoadBalancer createLoadBalancer(int i) {
        return LoadBalancer.createInstance(String.valueOf(i), i);
    }
}
