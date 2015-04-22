package pl.edu.agh.awi.persistence.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class LoadBalancer {

    @GraphId
    private Long id;

    @Indexed
    private String domain;

    private Integer load;

    public static LoadBalancer createInstance(String domain, Integer load) {
        LoadBalancer loadBalancer = new LoadBalancer();
        loadBalancer.load = load;
        loadBalancer.domain = domain;
        return loadBalancer;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadBalancer that = (LoadBalancer) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
