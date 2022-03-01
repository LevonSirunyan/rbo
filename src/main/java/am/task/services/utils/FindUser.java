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
    private final UserRepository mUserRepository;

    public FindUser(UserRepository userRepository) {
        this.mUserRepository = userRepository;
    }

    public User findUserByToken() throws EntityNotFoundException {
        Authentication authentication = AuthenticationUtil.getAuthentication();
        Optional<User> optionalUser = mUserRepository.findByEmail(authentication.getName());
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException(User.class, "userName", authentication.getName());
        }
        return optionalUser.get();
    }

}