package pl.edu.agh.awi.downloader.flights.balancer.client;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.balancer.data.LoadBalancerResponse;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoadBalancerClient extends AbstractFlightsClient<LoadBalancerResponse, HashMap<String, Integer>> {

    private static final String URL = "http://www.flightradar24.com/balance.json";

    private static Class<HashMap<String, Integer>> clazz = (Class<HashMap<String, Integer>>) new HashMap<String, Integer>().getClass();

    public LoadBalancerClient() {
        super(clazz);
    }


    @Override
    public LoadBalancerResponse getResponse() {
        Map<String, Integer> balancerNodes = load();

        LoadBalancerResponse loadBalancerNodes = new LoadBalancerResponse.LoadBalancerNodesBuilder()
                .setAvailableNodes(balancerNodes)
                .build();

        return loadBalancerNodes;
    }

    @Override
    protected String getUrl() {
        return URL;
    }

    @Override
    protected String getParameter() {
        return "";
    }
}
