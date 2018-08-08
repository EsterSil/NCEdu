package userservice.repositories;
/*
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
*/
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

//@NodeEntity
@Entity
public class Coordinate {

   // @Id
   // @GeneratedValue
    @Id
    private Long coordinateID;

    private Double latitude;
    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Timestamp timestamp;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCoordinateID() {
        return coordinateID;
    }

    public void setCoordinateID(Long coordinateID) {
        this.coordinateID = coordinateID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
/*
    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

   // @Relationship(type = "TEAMMATE", direction = Relationship.UNDIRECTED)
    //Set<Coordinate> coordinates;

    public void addNearestCoordinate( Coordinate coordinate){
        if(this.coordinates == null){
            this.coordinates = new HashSet<>();
        }
        this.coordinates.add(coordinate);
    }*/
}
