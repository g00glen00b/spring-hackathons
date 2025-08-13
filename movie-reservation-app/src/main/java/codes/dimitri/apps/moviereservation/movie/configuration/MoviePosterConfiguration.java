package codes.dimitri.apps.moviereservation.movie.configuration;

import codes.dimitri.apps.moviereservation.movie.repository.MoviePosterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
class MoviePosterConfiguration {
    @Bean
    MoviePosterRepository moviePosterRepository(MovieProperties properties) throws IOException {
        return new MoviePosterRepository(properties.postersLocation().getFile().toPath());
    }
}
