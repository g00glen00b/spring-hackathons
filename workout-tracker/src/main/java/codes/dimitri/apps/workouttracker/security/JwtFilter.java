package codes.dimitri.apps.workouttracker.security;

import com.auth0.jwt.JWTVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");
    private static final int BEARER_TOKEN_GROUP = 1;
    private final JWTVerifier jwtVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getToken(request)
            .map(jwtVerifier::verify)
            .map(jwt -> JwtAuthentication
                .builder()
                .jwt(jwt)
                .details(new WebAuthenticationDetailsSource().buildDetails(request))
                .build())
            .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        return Optional
            .ofNullable(request.getHeader(AUTHORIZATION_HEADER))
            .filter(not(String::isEmpty))
            .map(BEARER_PATTERN::matcher)
            .filter(Matcher::find)
            .map(matcher -> matcher.group(BEARER_TOKEN_GROUP));
    }
}
