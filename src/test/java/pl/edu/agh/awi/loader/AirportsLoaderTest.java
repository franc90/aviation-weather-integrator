package pl.edu.agh.awi.loader;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.awi.downloader.flights.airport.client.AirportClient;
import pl.edu.agh.awi.downloader.flights.airport.data.Airport;
import pl.edu.agh.awi.downloader.flights.airport.data.AirportResponse;
import pl.edu.agh.awi.loader.converter.AirportConverter;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.repositories.AirPortRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AirportsLoaderTest {

    @Mock
    private AirportClient airportClientMock;

    @Mock
    private AirportConverter airportConverterMock;

    @Mock
    private AirPortRepository airPortRepositoryMock;

    @Mock
    private AirportResponse airportResponseMock;

    @Mock
    private List<Airport> airportsFromResponeMock;

    @InjectMocks
    private AirportsLoader airportsLoader;

    private List<AirPort> givenAirPorts;

    @Before
    public void initMocks() {
        givenAirPorts = createGivenAirPorts();
        when(airportClientMock.getResponse()).thenReturn(airportResponseMock);
        when(airportResponseMock.getAirports()).thenReturn(airportsFromResponeMock);
        when(airportConverterMock.convert(airportsFromResponeMock)).thenReturn(givenAirPorts);
    }

    private List<AirPort> createGivenAirPorts() {
        String[] countries = {"United States", "Canada", "Mexico", "Poland", "Denmark"};
        return Stream.of(countries)
                    .map(this::createAirPortWithCountry)
                    .collect(Collectors.toList());
    }

    private AirPort createAirPortWithCountry(String country) {
        AirPort airPort = new AirPort();
        airPort.setCountry(country);
        return airPort;
    }

    @Test
    public void shouldLoadOnlyAirPortsForSupportedCountries() {
        airportsLoader.loadData();
        verify(airPortRepositoryMock).save(argThat(new AirPortListMatcher()));
    }

    private class AirPortListMatcher extends ArgumentMatcher<List<AirPort>> {

        private final Set<String> supportedCountries = Sets.newHashSet("United States", "Canada", "Mexico");

        @Override
        public boolean matches(Object argument) {
            List<AirPort> airPorts = (List<AirPort>) argument;
            return airPorts.stream()
                            .allMatch(airPort -> supportedCountries.contains(airPort.getCountry()));
        }
    }

}
