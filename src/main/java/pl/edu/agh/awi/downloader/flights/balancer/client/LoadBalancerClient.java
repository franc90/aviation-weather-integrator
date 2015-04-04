package pl.edu.agh.awi.downloader.flights.balancer.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;
import pl.edu.agh.awi.downloader.flights.balancer.data.LoadBalancerNodes;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoadBalancerClient {

    public static final String URL = "http://www.flightradar24.com/balance.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public LoadBalancerNodes getLoadBalancerNodes() {
        Map<String, Integer> balancerNodes = loadNodes();

        LoadBalancerNodes loadBalancerNodes = new LoadBalancerNodes.LoadBalancerNodesBuilder()
                .setAvailableNodes(balancerNodes)
                .build();

        return loadBalancerNodes;
    }

    private Map<String, Integer> loadNodes() {
        try {
            return objectMapper.readValue(new URL(URL), HashMap.class);
        } catch (IOException e) {
            throw new MalformedUrlException("Error while getting load balancer nodes", e);
        }
    }

}
