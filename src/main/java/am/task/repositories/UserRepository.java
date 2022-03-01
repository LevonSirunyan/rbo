package am.task.repositories;

import am.task.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<User> {
    Optional<User> findByEmail(String email);

}