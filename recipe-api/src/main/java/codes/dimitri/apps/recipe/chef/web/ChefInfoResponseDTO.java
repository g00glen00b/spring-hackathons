package codes.dimitri.apps.recipe.chef.web;

import codes.dimitri.apps.recipe.chef.Chef;
import codes.dimitri.apps.recipe.core.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.UUID;

public record ChefInfoResponseDTO(UUID id, String username, String displayname) {
    public static ChefInfoResponseDTO of(Chef chef) {
        return new ChefInfoResponseDTO(chef.getId(), chef.getUsername(), chef.getDisplayname());
    }

    public static ChefInfoResponseDTO of(DecodedJWT jwt) {
        return new ChefInfoResponseDTO(
            UUID.fromString(jwt.getSubject()),
            jwt.getClaim(JwtUtils.USERNAME_CLAIM).asString(),
            jwt.getClaim(JwtUtils.DISPLAYNAME_CLAIM).asString()
        );
    }
}
