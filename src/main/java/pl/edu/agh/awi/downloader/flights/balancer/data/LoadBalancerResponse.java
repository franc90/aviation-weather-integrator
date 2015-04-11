package pl.edu.agh.awi.downloader.flights.balancer.data;

import java.util.Map;

public class LoadBalancerResponse {

    private final Map<String, Integer> availableNodes;

    private LoadBalancerResponse(Map<String, Integer> availableNodes) {
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

        public LoadBalancerResponse build() {
            return new LoadBalancerResponse(availableNodes);
        }
    }

}
