package pl.edu.agh.awi.loader;

import com.google.common.collect.Lists;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.spring.context.SpringAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.persistence.repositories.ZoneRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@SpringAware
public class ZonesLoader extends AbstractLoader<ZoneResponse, pl.edu.agh.awi.downloader.flights.zone.data.Zone, Zone> {

    private static final String CACHED_ZONES = "zones";

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    private IList<Zone> cachedZones;

    @Override
    public void loadData() {
        ZoneResponse response = client.getResponse();
        List<Zone> zones = converter.convert(response.getZones());
        Iterable<Zone> savedZones = zoneRepository.save(zones);
        cacheZones(savedZones);
    }

    private void cacheZones(Iterable<Zone> zones) {
        cachedZones.clear();
        cachedZones.addAll(Lists.newArrayList(zones));
    }

    @PostConstruct
    private void initCachedZonesList() {
        cachedZones = hazelcastInstance.getList(CACHED_ZONES);
    }

}
