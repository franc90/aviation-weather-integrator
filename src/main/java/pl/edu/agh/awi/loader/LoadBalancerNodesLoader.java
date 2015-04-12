package pl.edu.agh.awi.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.balancer.data.LoadBalancerResponse;
import pl.edu.agh.awi.loader.converter.LoaderConverter;
import pl.edu.agh.awi.persistence.model.LoadBalancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LoadBalancerNodesLoader implements StartupLoader {

    @Autowired
    private AbstractFlightsClient<LoadBalancerResponse, ?> client;

    @Autowired
    private LoaderConverter<Map.Entry<String, Integer>, LoadBalancer> converter;

    @Override
    public void loadData() {
        LoadBalancerResponse response = client.getResponse();
        Map<String, Integer> availableNodes = response.getAvailableNodes();
        List<LoadBalancer> loadBalancers = converter.convert(new ArrayList<>(availableNodes.entrySet()));

        // TODO: save to db
        System.out.println("LOAD_BALANCER_NODES_LOADER: " + loadBalancers.size());
    }

}
