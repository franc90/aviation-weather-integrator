package pl.edu.agh.awi.loader.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.persistence.model.LoadBalancer;

import java.util.Map;

@Component
public class LoadBalancerConverter implements LoaderConverter<Map.Entry<String, Integer>, LoadBalancer> {

    @Override
    public LoadBalancer apply(Map.Entry<String, Integer> entry) {
        LoadBalancer node = new LoadBalancer();
        node.setDomain(entry.getKey());
        node.setLoad(entry.getValue());
        return node;
    }

}
