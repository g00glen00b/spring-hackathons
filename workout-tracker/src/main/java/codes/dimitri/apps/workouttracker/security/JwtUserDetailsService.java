package codes.dimitri.apps.workouttracker.security;

import codes.dimitri.apps.workouttracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .map(user -> JwtUser.of(user, jwtUtils.token(user)))
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
