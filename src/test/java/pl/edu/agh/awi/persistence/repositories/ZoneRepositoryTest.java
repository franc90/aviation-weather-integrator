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
import pl.edu.agh.awi.persistence.model.Zone;

import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
@Transactional
public class ZoneRepositoryTest {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Test
    public void shouldFindWidestZone() {
        save(new Zone.Builder()
                .withName("small")
                .withLatitude(20.0, 30.0)
                .withLongitude(4.1, 4.4)
                .build());
        Zone big = save(new Zone.Builder()
                .withName("big")
                .withLatitude(-20.0, 30.0)
                .withLongitude(4.1, 24.4)
                .build());
        Zone zone = zoneRepository.findWidestZone();
        assertEquals(big.getName(), zone.getName());
    }

    private Zone save(Zone zone) {
        return neo4jTemplate.save(zone);
    }

    @Test
    public void shouldSaveZoneWithRelatedSubZones() {
        int numberOfSubzonesPerZone = 2;
        int depthOfSubzonesChain = 3;
        Zone zone = createZonesChain(numberOfSubzonesPerZone, depthOfSubzonesChain);
        zoneRepository.save(zone);
        BigInteger expectedCount = calculateExpectedCount(numberOfSubzonesPerZone, depthOfSubzonesChain);
        BigInteger actualCount = BigInteger.valueOf(neo4jTemplate.count(Zone.class));
        assertTrue(expectedCount.compareTo(actualCount) == 0);
    }

    private Zone createZonesChain(int numberOfSubzonesPerZone, int depthOfSubzonesChain) {
        if (depthOfSubzonesChain == 0)
            return new Zone();
        if (depthOfSubzonesChain > 1) {
            final int decremented = --depthOfSubzonesChain;
            return createZoneWithSubzones(numberOfSubzonesPerZone, () -> createZonesChain(numberOfSubzonesPerZone, decremented));
        }
        return createZoneWithSubzones(numberOfSubzonesPerZone, Zone::new);
    }

    private Zone createZoneWithSubzones(int numberOfSubzonesPerZone, Supplier<Zone> zoneSupplier) {
        Zone zone = new Zone();
        IntStream.range(0, numberOfSubzonesPerZone)
                .forEach(i -> zone.addSubzone(zoneSupplier.get()));
        return zone;
    }

    private BigInteger calculateExpectedCount(int numberOfSubzonesPerZone, int depthOfSubzonesChain) {
        BigInteger base = BigInteger.valueOf(numberOfSubzonesPerZone);
        return IntStream.range(0, depthOfSubzonesChain + 1)
                .mapToObj(base::pow)
                .reduce(BigInteger::add)
                .get();
    }

}
