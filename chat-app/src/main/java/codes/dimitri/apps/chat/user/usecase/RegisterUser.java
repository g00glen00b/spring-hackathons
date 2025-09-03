package codes.dimitri.apps.chat.user.usecase;

import codes.dimitri.apps.chat.shared.UseCase;
import codes.dimitri.apps.chat.user.User;
import codes.dimitri.apps.chat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@UseCase
@RequiredArgsConstructor
public class RegisterUser {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public record Parameters(String username, String password, String repeatPassword) {
        public Parameters {
            Assert.notNull(username, "username cannot be empty");
            Assert.state(!username.isBlank(), "username cannot be empty");
            Assert.state(username.length() <= 128, "username cannot be longer than 128 characters");
            Assert.notNull(password, "password cannot be null");
            Assert.state(password.equals(repeatPassword), "passwords do not match");
        }
    }

    public User execute(Parameters parameters) {
        return repository.save(new User(
            parameters.username(),
            passwordEncoder.encode(parameters.password())
        ));
    }
}
