package codes.dimitri.apps.workouttracker.user.usecase;

import codes.dimitri.apps.workouttracker.UseCase;
import codes.dimitri.apps.workouttracker.user.User;
import codes.dimitri.apps.workouttracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class RegisterUser {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public record Parameters(String username, String password) { }

    public User execute(Parameters parameters) {
        if (repository.existsByUsername(parameters.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        var user = new User(parameters.username(), passwordEncoder.encode(parameters.password()));
        return repository.save(user);
    }
}
