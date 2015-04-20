package pl.edu.agh.awi.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.balancer.data.LoadBalancerResponse;
import pl.edu.agh.awi.persistence.model.LoadBalancer;
import pl.edu.agh.awi.persistence.repositories.LoadBalancerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LoadBalancerNodesLoader extends AbstractLoader<LoadBalancerResponse, Map.Entry<String, Integer>, LoadBalancer> {

    @Autowired
    private LoadBalancerRepository loadBalancerRepository;

    @Override
    public void loadData() {
        LoadBalancerResponse response = client.getResponse();
        Map<String, Integer> availableNodes = response.getAvailableNodes();
        List<LoadBalancer> loadBalancers = converter.convert(new ArrayList<>(availableNodes.entrySet()));
        loadBalancerRepository.save(loadBalancers);
    }

}
