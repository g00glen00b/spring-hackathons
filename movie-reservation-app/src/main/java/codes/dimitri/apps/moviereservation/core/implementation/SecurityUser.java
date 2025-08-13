package codes.dimitri.apps.moviereservation.core.implementation;

import codes.dimitri.apps.moviereservation.user.User;
import codes.dimitri.apps.moviereservation.user.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityUser implements UserDetails {
    private static final List<GrantedAuthority> ADMIN_AUTHORITIES = List.of(new SimpleGrantedAuthority("ADMIN"));
    private static final List<GrantedAuthority> USER_AUTHORITIES = List.of();
    private final UserId id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public static SecurityUser ofUser(User user) {
        return new SecurityUser(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.isAdmin() ? ADMIN_AUTHORITIES : USER_AUTHORITIES
        );
    }
}
