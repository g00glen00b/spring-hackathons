package codes.dimitri.apps.leaderboard.shared;

import codes.dimitri.apps.leaderboard.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record SecurityUser(UUID id, String username, String password) implements UserDetails {
    public static SecurityUser from(User user) {
        return new SecurityUser(user.getId(), user.getUsername(), user.getPassword());
    }

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
}
