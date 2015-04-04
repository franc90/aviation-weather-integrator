package pl.edu.agh.awi.downloader.flights.balancer.data;

import java.util.Map;

public class LoadBalancerNodes {

    private final Map<String, Integer> availableNodes;

    private LoadBalancerNodes(Map<String, Integer> availableNodes) {
        this.availableNodes = availableNodes;
    }

    public Map<String, Integer> getAvailableNodes() {
        return availableNodes;
    }

    public static class LoadBalancerNodesBuilder {
        private Map<String, Integer> availableNodes;

        public LoadBalancerNodesBuilder setAvailableNodes(Map<String, Integer> availableNodes) {
            this.availableNodes = availableNodes;
            return this;
        }

        public LoadBalancerNodes build() {
            return new LoadBalancerNodes(availableNodes);
        }
    }

}
