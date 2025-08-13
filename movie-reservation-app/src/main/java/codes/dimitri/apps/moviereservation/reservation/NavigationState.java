package codes.dimitri.apps.moviereservation.reservation;

import codes.dimitri.apps.moviereservation.movie.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record NavigationState(MovieNavigationState movie, LocalDate date, LocalTime time) {
    public record MovieNavigationState(UUID id, String title) {
        public static MovieNavigationState of(Movie movie) {
            return new MovieNavigationState(movie.getId().getId(), movie.getTitle());
        }
    }

    public static NavigationState empty() {
        return new NavigationState(null, null, null);
    }

    public static NavigationState of(Movie movie) {
        return new NavigationState(MovieNavigationState.of(movie), null, null);
    }

    public static NavigationState of(Movie movie, LocalDate date) {
        return new NavigationState(MovieNavigationState.of(movie), date, null);
    }

    public static NavigationState of(Movie movie, LocalDateTime dateTime) {
        return new NavigationState(MovieNavigationState.of(movie), dateTime.toLocalDate(), dateTime.toLocalTime());
    }
}
