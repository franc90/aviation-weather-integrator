package pl.edu.agh.awi.loader;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.persistence.repositories.ZoneRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ZonesLoader extends AbstractLoader<ZoneResponse, pl.edu.agh.awi.downloader.flights.zone.data.Zone, Zone> {

    private static final Logger logger = Logger.getLogger("ZonesLoader");

    private static final String CACHED_ZONES = "zones";

    @Autowired
    private ZoneRepository zoneRepository;

    @Resource
    private List<Zone> zones;

    @Override
    public void loadData() {
        ZoneResponse response = client.getResponse();
        List<Zone> zones = converter.convert(response.getZones());
        Iterable<Zone> savedZones = zoneRepository.saveIfNotExists(zones);
        cacheZones(savedZones);
        logger.info("Zones loaded");
    }

    private void cacheZones(Iterable<Zone> zones) {
        this.zones.clear();
        this.zones.addAll(Lists.newArrayList(zones));
    }

}
