package codes.dimitri.apps.moviereservation.movie;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private MovieId id;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private Duration duration;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
        name = "movie_genre",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
    @OneToMany(mappedBy = "movie", cascade =  CascadeType.ALL)
    private List<Showtime> showtimes;

    public Movie(MovieId id, String title, String description, Duration duration, List<Genre> genres) {
        this(id, title, description, duration, genres, new ArrayList<>());
    }

    public void setGenres(List<Genre> genres) {
        this.genres.clear();
        this.genres.addAll(genres);
    }
}
