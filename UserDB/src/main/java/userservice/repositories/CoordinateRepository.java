package userservice.repositories;

import org.springframework.data.repository.CrudRepository;

public interface CoordinateRepository extends CrudRepository<Coordinate, Long> {

    Coordinate findCoordinateByUser(Long userID);
}
