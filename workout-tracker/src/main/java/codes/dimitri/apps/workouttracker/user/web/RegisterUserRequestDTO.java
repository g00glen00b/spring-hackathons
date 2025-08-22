package codes.dimitri.apps.workouttracker.user.web;

import codes.dimitri.apps.workouttracker.user.usecase.RegisterUser;

public record RegisterUserRequestDTO(String username, String password) {
    public RegisterUser.Parameters toParameters() {
        return new RegisterUser.Parameters(username, password);
    }
}
