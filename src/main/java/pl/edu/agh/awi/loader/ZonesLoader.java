package pl.edu.agh.awi.loader;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;
import pl.edu.agh.awi.persistence.model.Zone;

import java.util.List;

@Component
public class ZonesLoader extends AbstractLoader<ZoneResponse, pl.edu.agh.awi.downloader.flights.zone.data.Zone, Zone> {

    @Override
    public void loadData() {
        ZoneResponse response = client.getResponse();
        List<Zone> zones = converter.convert(response.getZones());

        // TODO: save to db
        // TODO: to cache?
        System.out.println("ZONES_LOADER: " + zones.size());
    }
}
