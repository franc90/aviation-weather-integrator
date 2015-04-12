package pl.edu.agh.awi.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;
import pl.edu.agh.awi.loader.converter.LoaderConverter;
import pl.edu.agh.awi.persistence.model.Zone;

import java.util.List;

@Component
public class ZonesLoader implements StartupLoader {

    @Autowired
    private AbstractFlightsClient<ZoneResponse, ?> client;

    @Autowired
    private LoaderConverter<pl.edu.agh.awi.downloader.flights.zone.data.Zone, Zone> converter;

    @Override
    public void loadData() {
        ZoneResponse response = client.getResponse();
        List<Zone> zones = converter.convert(response.getZones());

        // TODO: save to db
        // TODO: to cache?
        System.out.println("ZONES_LOADER: " + zones.size());
    }
}
