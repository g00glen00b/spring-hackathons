package codes.dimitri.apps.recipe.chef.usecase;

import codes.dimitri.apps.recipe.UseCase;
import codes.dimitri.apps.recipe.chef.Chef;
import codes.dimitri.apps.recipe.chef.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@UseCase(readOnly = false)
@RequiredArgsConstructor
public class RegisterChef {
    private final ChefRepository repository;
    private final PasswordEncoder passwordEncoder;

    public record Parameters(String username, String displayname, String password) {
        public Parameters {
            Assert.notNull(username, "username cannot be null");
            Assert.notNull(password, "password cannot be null");
            Assert.notNull(displayname, "displayname cannot be null");
            Assert.state(username.length() <= 128, "username cannot be longer than 128 characters");
            Assert.state(displayname.length() <= 128, "displayname cannot be longer than 128 characters");
        }
    }

    public Chef execute(Parameters parameters) {
        if (repository.existsByUsername(parameters.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        var encodedPassword = passwordEncoder.encode(parameters.password());
        var user = new Chef(parameters.username, parameters.displayname, encodedPassword);
        return repository.save(user);
    }
}
