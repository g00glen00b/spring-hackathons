package codes.dimitri.apps.workouttracker.security;

import codes.dimitri.apps.workouttracker.user.User;
import codes.dimitri.apps.workouttracker.user.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Algorithm algorithm;
    private final JwtProperties properties;

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .map(user -> JwtUser.of(user, createToken(user)))
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private String createToken(User entity) {
        return JWT
            .create()
            .withIssuer(properties.issuer())
            .withSubject(entity.getUsername())
            .withExpiresAt(Instant.now().plus(properties.expiration()))
            .sign(algorithm);
    }
}
