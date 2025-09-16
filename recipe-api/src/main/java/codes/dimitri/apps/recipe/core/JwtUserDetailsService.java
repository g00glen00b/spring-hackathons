package codes.dimitri.apps.recipe.core;

import codes.dimitri.apps.recipe.chef.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JwtUserDetailsService implements UserDetailsService {
    private final ChefRepository chefRepository;
    private final JwtUtils jwtUtils;

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return chefRepository
            .findByUsername(username)
            .map(chef -> JwtUser.of(chef, jwtUtils.token(chef)))
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
