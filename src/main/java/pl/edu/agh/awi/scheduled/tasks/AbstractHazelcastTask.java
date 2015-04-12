package pl.edu.agh.awi.scheduled.tasks;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.context.SpringAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringAware
public abstract class AbstractHazelcastTask {

    @Autowired
    protected HazelcastInstance hazelcast;

    @PostConstruct
    protected abstract void init();
}
