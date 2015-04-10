package pl.edu.agh.awi.persistence.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "to")
public class DestinationAirPort {
    @GraphId
    private Long id;

    private Long ordinalNumber;

    @StartNode
    private Flight flight;

    @EndNode
    private AirPort arrivalAirPort;
}
