package codes.dimitri.apps.recipe.core;

import codes.dimitri.apps.recipe.chef.Chef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record JwtUser(String username, String password, String jwt) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static JwtUser of(Chef entity, String jwt) {
        return new JwtUser(entity.getUsername(), entity.getPassword(), jwt);
    }
}
