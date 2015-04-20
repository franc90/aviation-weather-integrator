package pl.edu.agh.awi.loader.file_loader;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AirportCityLoader implements FileLoader<String> {

    private static final String RUNWAYS_CSV = "/airport/airportCities.csv";
    private static final String NO_DATA = "";
    private static final String SEPARATOR = ",";
    private static final String ENCODING = "UTF-8";

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
            File file = FileUtils.toFile(getClass().getResource(RUNWAYS_CSV));
            String read = FileUtils.readFileToString(file, ENCODING);

            cities = Stream.of(read.split(System.lineSeparator()))
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
