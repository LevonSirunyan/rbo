package am.task.controllers;

import am.task.config.securityConfig.TokenProvider;
import am.task.model.ResponseModel;
import am.task.model.dto.AuthTokenDTO;
import am.task.model.dto.user.LoginUserDto;
import am.task.model.dto.user.UserCreatingDto;
import am.task.model.entity.User;
import am.task.services.user.UserService;
import am.task.services.utils.DecodeTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth/")
@Api(value = "authentication")
public class AuthenticationController extends BaseController {
    private static final String USERNAME_OR_PASSWORD_IS_INCORRECT = "User name or password is incorrect !!!";

    @Lazy
    private final AuthenticationManager authenticationManager;
    @Lazy
    private final TokenProvider tokenProvider;
    @Lazy
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenProvider tokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }


    @PostMapping("signIn")
    public ResponseModel login(@Valid @RequestBody LoginUserDto loginUser) {
        try {
            Optional<User> optionalUser = userService.getByUsername(loginUser.getEmail());

            if (optionalUser.isEmpty())
                return ResponseModel.builder()
                        .success(false)
                        .message(USERNAME_OR_PASSWORD_IS_INCORRECT)
                        .build();

            return returnToken(loginUser.getEmail(), loginUser.getPassword());
        } catch (Exception e) {
            return ResponseModel.builder()
                    .success(false)
                    .message(USERNAME_OR_PASSWORD_IS_INCORRECT)
                    .build();
        }
    }

    @PostMapping("signUp")
    public ResponseModel registration(@Valid @RequestBody UserCreatingDto userCreatingDto) {
        try {
            userService.registration(userCreatingDto);
            return returnToken(userCreatingDto.getEmail(), userCreatingDto.getPassword());
        } catch (AuthenticationException e) {
            return createFailResult(USERNAME_OR_PASSWORD_IS_INCORRECT);
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    private ResponseModel returnToken(String email, String password) {
        final Authentication newAuthentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        final String token = tokenProvider.generateToken(newAuthentication);
        final String roleName = DecodeTokenUtil.getRoleFromToken(token);
        AuthTokenDTO authTokenDTO = AuthTokenDTO.builder()
                .role(roleName)
                .token(token)
                .build();
        return ResponseModel.builder().data(authTokenDTO)
                .success(true)
                .message("OK")
                .build();
    }

}