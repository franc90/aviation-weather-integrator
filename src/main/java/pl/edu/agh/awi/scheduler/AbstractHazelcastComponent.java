package pl.edu.agh.awi.scheduler;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.context.SpringAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringAware
public abstract class AbstractHazelcastComponent {

    @Autowired
    protected HazelcastInstance hazelcast;

    @PostConstruct
    protected abstract void init();

}
