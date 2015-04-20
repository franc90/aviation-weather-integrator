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
public class RunwaysLoader implements FileLoader<Long> {

    private static final String RUNWAYS_CSV = "/airport/runways.csv";
    private static final long NO_DATA = 0L;
    private static final String ENCODING = "UTF-8";

    private Map<String, Long> runways;

    @Override
    public void clear() {
        runways = null;
    }

    @Override
    public Long getValue(String airportIcao) {
        if (StringUtils.isEmpty(airportIcao)) {
            return NO_DATA;
        }

        return getRunwaysCount(airportIcao).orElse(NO_DATA);
    }


    private Optional<Long> getRunwaysCount(String ariportIcao) {
        if (CollectionUtils.isEmpty(runways)) {
            loadRunways();
        }
        return Optional.ofNullable(runways.get(ariportIcao));
    }

    private void loadRunways() {
        try {
            File file = FileUtils.toFile(getClass().getResource(RUNWAYS_CSV));
            String read = FileUtils.readFileToString(file, ENCODING);

            runways = Stream
                    .of(read.split(System.lineSeparator()))
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
