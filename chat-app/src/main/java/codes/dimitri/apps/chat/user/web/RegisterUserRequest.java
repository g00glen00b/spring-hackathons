package codes.dimitri.apps.chat.user.web;

import codes.dimitri.apps.chat.user.usecase.RegisterUser;

public record RegisterUserRequest(String username, String password, String repeatPassword) {
    public static RegisterUserRequest blank() {
        return new RegisterUserRequest("", "", "");
    }

    public RegisterUser.Parameters toParameters() {
        return new RegisterUser.Parameters(username, password, repeatPassword);
    }
}
