package am.task.services.user;

import am.task.enums.UserRoleEnum;
import am.task.enums.UserStatusEnum;
import am.task.model.dto.UserCreatingDto;
import am.task.model.entity.User;
import am.task.repositories.UserRepository;
import am.task.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService, UserDetailsService {
    @Lazy
    private final UserRepository userRepository;
    @Lazy
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Email %s doesn't exist", username));
        } else {
            User user = optionalUser.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
        }
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void registration(UserCreatingDto userCreatingDto) throws IllegalArgumentException {
        Optional<User> userOptional = userRepository.findByEmail(userCreatingDto.getEmail());
        if (userOptional.isPresent() && userOptional.get().getFirstName() != null && userOptional.get().getEmail() != null) {
            throw new IllegalArgumentException("An account with this Email already exists.");
        }
        User user = User.builder()
                .firstName(userCreatingDto.getFirstName())
                .lastName(userCreatingDto.getLastName())
                .password(passwordEncoder.encode(userCreatingDto.getPassword()))
                .email(userCreatingDto.getEmail())
                .userStatusEnum(UserStatusEnum.ACTIVE)
                .role(UserRoleEnum.ROLE_USER)
                .build();
        userRepository.saveAndFlush(user);
    }

}