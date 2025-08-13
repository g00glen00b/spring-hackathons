package codes.dimitri.apps.moviereservation.user.usecase;

import codes.dimitri.apps.moviereservation.core.implementation.UseCase;
import codes.dimitri.apps.moviereservation.user.User;
import codes.dimitri.apps.moviereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class RegisterUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User execute(RegisterUserParameters parameters) {
        validateUniqueUsername(parameters);
        return userRepository.save(new User(
            userRepository.nextId(),
            parameters.username(),
            passwordEncoder.encode(parameters.password())
        ));
    }

    private void validateUniqueUsername(RegisterUserParameters parameters) {
        Optional<User> result = userRepository.findByUsername(parameters.username());
        Assert.state(result.isEmpty(), "username already exists");
    }
}
