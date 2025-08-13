package codes.dimitri.apps.moviereservation.user.web;

import codes.dimitri.apps.moviereservation.user.usecase.RegisterUserParameters;

public record RegisterUserRequest(String username, String password, String repeatPassword) {
    public static RegisterUserRequest blank() {
        return new RegisterUserRequest("", "", "");
    }

    public RegisterUserParameters toParameters() {
        return new RegisterUserParameters(username, password, repeatPassword);
    }
}
