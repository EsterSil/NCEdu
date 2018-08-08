package userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   // List<User> getUserByFirstName(String firstName);

    User getUserByLogin(String login);
}
