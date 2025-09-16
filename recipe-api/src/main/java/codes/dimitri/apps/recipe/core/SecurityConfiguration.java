package codes.dimitri.apps.recipe.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
class SecurityConfiguration {
    @Bean
    @Order(1)
    SecurityFilterChain usernamePasswordChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/api/chef/token")
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(withDefaults())
            .build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain jwtChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.POST, "/api/chef").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/recipe").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/recipe/image/**").permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterAfter(jwtFilter, SecurityContextHolderAwareRequestFilter.class)
            .build();
    }

    @Bean
    Algorithm algorithm(JwtProperties properties) {
        return Algorithm.HMAC256(properties.secret());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    FilterRegistrationBean<?> jwtFilterRegistration(JwtFilter jwtFilter) {
        FilterRegistrationBean<?> registration = new FilterRegistrationBean<>(jwtFilter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    JWTVerifier jwtVerifier(Algorithm algorithm, JwtProperties properties) {
        return JWT
            .require(algorithm)
            .withIssuer(properties.issuer())
            .build();
    }
}
