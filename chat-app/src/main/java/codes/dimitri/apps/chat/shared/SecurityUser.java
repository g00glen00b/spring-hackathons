package codes.dimitri.apps.chat.shared;

import codes.dimitri.apps.chat.user.User;
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

    public boolean isSameUser(User user) {
        return id.equals(user.getId());
    }
}
