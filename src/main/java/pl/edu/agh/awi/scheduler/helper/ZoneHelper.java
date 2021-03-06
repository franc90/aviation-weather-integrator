package pl.edu.agh.awi.scheduler.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.scheduler.exception.SchedulerException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ZoneHelper {

    @Autowired
    private PersistenceService persistenceService;

    @Resource
    private List<Zone> zones;

    public Zone loadZone(String zoneName) {
        if (CollectionUtils.isEmpty(zones)) {
            return getZone(loadZoneFromDb(), zoneName);
        }

        return getZone(zones, zoneName);
    }

    private Zone getZone(List<Zone> zones, String zoneName) {
        for (Zone zone : zones) {
            if (zoneName.equals(zone.getName())) {
                return zone;
            }
        }

        throw new SchedulerException("No zone " + zoneName);
    }

    private List<Zone> loadZoneFromDb() {
        Collection<Zone> zones = persistenceService.findAllZones();

        if (CollectionUtils.isEmpty(zones)) {
            throw new SchedulerException("No zones in database nor cache");
        }

        return new ArrayList<>(zones);
    }
}
