package pl.edu.agh.awi.loader.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.persistence.model.Zone;

@Component
public class ZoneConverter implements LoaderConverter<pl.edu.agh.awi.downloader.flights.zone.data.Zone, Zone> {

    @Override
    public Zone apply(pl.edu.agh.awi.downloader.flights.zone.data.Zone z) {
        Zone zone = new Zone();
        zone.setMaximalLatitude(z.getMaximalLatitude());
        zone.setMinimalLatitude(z.getMinimalLatitude());
        zone.setMaximalLongitude(z.getMaximalLongitude());
        zone.setMinimalLongitude(z.getMinimalLongitude());
        zone.setName(z.getName());

        z.getSubzones().stream().map(x -> apply(x)).forEach(e -> zone.addSubzone(e));

        return zone;
    }
}
