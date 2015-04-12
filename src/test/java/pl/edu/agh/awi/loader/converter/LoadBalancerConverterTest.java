package pl.edu.agh.awi.loader.converter;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.awi.persistence.model.LoadBalancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoadBalancerConverterTest {

    public static final String DOMAIN = "domain";
    public static final String DOMAIN2 = "domain2";
    public static final Integer LOAD = 1;
    public static final Integer LOAD2 = 2;

    private Map<String, Integer> availableNodes;

    @Before
    public void init() {
        availableNodes = new HashMap<>();
        availableNodes.put(DOMAIN, LOAD);
        availableNodes.put(DOMAIN2, LOAD2);
    }

    @Test
    public void checkConversion() {
        List<LoadBalancer> loadBalancers = new LoadBalancerConverter().convert(new ArrayList<>(availableNodes.entrySet()));

        assertNotNull(loadBalancers);
        assertEquals(loadBalancers.size(), 2);

        LoadBalancer loadBalancer = loadBalancers.get(0);
        assertEquals(loadBalancer.getDomain(), DOMAIN);
        assertEquals(loadBalancer.getLoad(), LOAD);
    }

}