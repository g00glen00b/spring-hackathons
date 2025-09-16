package codes.dimitri.apps.recipe.chef.web;

import codes.dimitri.apps.recipe.chef.usecase.RegisterChef;

public record RegisterChefRequestDTO(String username, String displayname, String password) {
    public RegisterChef.Parameters toParameters() {
        return new RegisterChef.Parameters(username, displayname, password);
    }
}
