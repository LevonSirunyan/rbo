package am.task.services.utils;

import am.task.exceptions.EntityNotFoundException;
import am.task.model.entity.User;
import am.task.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindUser {

    @Lazy
    private final UserRepository userRepository;

    public FindUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByToken() throws EntityNotFoundException {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "userName", authentication.getName()));
    }

    public User findUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(id)));
    }

}