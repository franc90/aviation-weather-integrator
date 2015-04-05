package pl.edu.agh.awi.downloader.flights.balancer.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.balancer.data.LoadBalancerResponse;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class LoadBalancerClientTest {

    @Test
    public void testRetrievingData() {
        LoadBalancerClient client = new LoadBalancerClient();
        LoadBalancerResponse loadBalancerNodes = client.getLoadBalancerNodes();

        assertNotNull(loadBalancerNodes);

        Map<String, Integer> availableNodes = loadBalancerNodes.getAvailableNodes();
        assertNotNull(availableNodes);
        assertFalse(availableNodes.isEmpty());
    }

}