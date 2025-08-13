package codes.dimitri.apps.moviereservation.user.usecase;

import org.springframework.util.Assert;

public record RegisterUserParameters(String username, String password, String repeatPassword) {
    public RegisterUserParameters {
        Assert.notNull(username, "username cannot be empty");
        Assert.state(!username.isBlank(), "username cannot be empty");
        Assert.state(username.length() <= 128, "username cannot be longer than 128 characters");
        Assert.notNull(password, "password cannot be null");
        Assert.state(password.equals(repeatPassword), "passwords do not match");
    }
}
