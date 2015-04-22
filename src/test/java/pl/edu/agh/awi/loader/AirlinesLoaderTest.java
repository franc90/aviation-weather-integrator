package pl.edu.agh.awi.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.awi.downloader.flights.airline.client.AirlineClient;
import pl.edu.agh.awi.downloader.flights.airline.data.Airline;
import pl.edu.agh.awi.downloader.flights.airline.data.AirlineResponse;
import pl.edu.agh.awi.loader.converter.AirlineConverter;
import pl.edu.agh.awi.persistence.model.AirLine;
import pl.edu.agh.awi.persistence.repositories.AirLineRepository;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AirlinesLoaderTest {

    @Mock
    private AirlineClient airlineClientMock;

    @Mock
    private AirlineResponse airlineResponseMock;

    @Mock
    private List<Airline> airlinesFromResponseMock;

    @Mock
    private AirlineConverter airlineConverterMock;

    @Mock
    private List<AirLine> convertedAirLinesMock;

    @Mock
    private AirLineRepository airLineRepositoryMock;

    @InjectMocks
    private AirlinesLoader airlinesLoader;

    @Before
    public void initMocks() {
        when(airlineClientMock.getResponse()).thenReturn(airlineResponseMock);
        when(airlineResponseMock.getAirlines()).thenReturn(airlinesFromResponseMock);
        when(airlineConverterMock.convert(airlinesFromResponseMock)).thenReturn(convertedAirLinesMock);
    }

    @Test
    public void shouldLoadAirlines() {
        airlinesLoader.loadData();
        verify(airLineRepositoryMock).saveIfNotExists(convertedAirLinesMock);
    }

}
