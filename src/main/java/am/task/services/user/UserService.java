package am.task.services.user;

import am.task.model.dto.user.UserCreatingDto;
import am.task.model.entity.User;
import am.task.services.CommonService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService extends CommonService<User> {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Optional<User> getByUsername(String name);

    Optional<User> findById(Long id);

    void registration(UserCreatingDto userCreatingDto) throws IllegalArgumentException;
}