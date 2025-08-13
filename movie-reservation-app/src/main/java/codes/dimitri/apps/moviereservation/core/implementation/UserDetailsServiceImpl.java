package codes.dimitri.apps.moviereservation.core.implementation;

import codes.dimitri.apps.moviereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .map(SecurityUser::ofUser)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
