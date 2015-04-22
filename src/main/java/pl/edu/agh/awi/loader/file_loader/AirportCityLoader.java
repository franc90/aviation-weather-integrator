package pl.edu.agh.awi.loader.file_loader;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AirportCityLoader implements FileLoader<String> {

    private static final String RUNWAYS_CSV = "airport/airportCities.csv";
    private static final String NO_DATA = "";
    private static final String SEPARATOR = ",";

    private Map<String, String> cities;

    @Override
    public void clear() {
        cities = null;
    }

    @Override
    public String getValue(String airportIcao) {
        if (StringUtils.isEmpty(airportIcao)) {
            return NO_DATA;
        }

        return getRunwaysCount(airportIcao).orElse(NO_DATA);
    }


    private Optional<String> getRunwaysCount(String ariportIcao) {
        if (CollectionUtils.isEmpty(cities)) {
            loadRunways();
        }
        return Optional.ofNullable(cities.get(ariportIcao));
    }

    private void loadRunways() {
        try {
            List<String> read = IOUtils.readLines(ClassLoader.getSystemResourceAsStream(RUNWAYS_CSV), "UTF-8");

            cities = read
                    .stream()
                    .filter(e -> e.split(SEPARATOR).length == 2)
                    .collect(Collectors.toMap(
                                    (String e) -> e.split(SEPARATOR)[0],
                                    (String e) -> e.split(SEPARATOR)[1])
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
