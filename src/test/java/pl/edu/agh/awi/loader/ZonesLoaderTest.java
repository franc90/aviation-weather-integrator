package pl.edu.agh.awi.loader;

import com.google.common.collect.Lists;
import com.hazelcast.core.IList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.awi.downloader.flights.zone.client.ZoneClient;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;
import pl.edu.agh.awi.loader.converter.ZoneConverter;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.persistence.repositories.ZoneRepository;

import java.util.List;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZonesLoaderTest {

    @Mock
    private ZoneClient zoneClientMock;

    @Mock
    private ZoneConverter zoneConverterMock;

    @Mock
    private ZoneRepository zoneRepositoryMock;

    @Mock
    private ZoneResponse zoneResponseMock;

    @Mock
    private IList<Zone> cachedZonesMock;

    @Mock
    private List<pl.edu.agh.awi.downloader.flights.zone.data.Zone> zonesFromResponseMock;

    @Mock
    private List<Zone> convertedZonesMock;

    @Mock
    private Zone givenZone;

    @InjectMocks
    private ZonesLoader zonesLoader;

    @Before
    public void initMocks() {
        Iterable<Zone> givenSavedZones = Lists.newArrayList(givenZone);
        when(zoneClientMock.getResponse()).thenReturn(zoneResponseMock);
        when(zoneResponseMock.getZones()).thenReturn(zonesFromResponseMock);
        when(zoneConverterMock.convert(zonesFromResponseMock)).thenReturn(convertedZonesMock);
        when(zoneRepositoryMock.saveIfNotExists(convertedZonesMock)).thenReturn(givenSavedZones);
    }

    @Test
    public void shouldLoadAndCacheZones() {
        zonesLoader.loadData();
        verify(zoneRepositoryMock).saveIfNotExists(convertedZonesMock);
        verify(cachedZonesMock).clear();
        verify(cachedZonesMock).addAll(argThat(new ZoneListToCacheMatcher()));
    }

    private class ZoneListToCacheMatcher extends ArgumentMatcher<List<Zone>> {

        @Override
        public boolean matches(Object argument) {
            List<Zone> zones = (List<Zone>) argument;
            return zones.contains(givenZone);
        }
    }
}
