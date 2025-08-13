package codes.dimitri.apps.moviereservation.movie.web;

import codes.dimitri.apps.moviereservation.movie.GenreId;
import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.MovieId;
import codes.dimitri.apps.moviereservation.movie.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final FilterMovies filterMovies;
    private final GetMoviePoster getMoviePoster;
    private final GetMovieInfo getMovieInfo;
    private final GetMovieStatistics getMovieStatistics;
    private final ListAllFutureShowtimeDates listAllFutureShowtimeDates;
    private final DeleteMovie deleteMovie;
    private final ListAllGenres listAllGenres;
    private final RegisterMovie registerMovie;


    @GetMapping
    public String movies(
        @RequestParam(required = false) UUID genreId,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "8") int size,
        ModelMap model) {
        var parameters = new FilterMoviesParameters(
            genreId == null ? null : new GenreId(genreId),
            page,
            size
        );
        Page<Movie> result = filterMovies.execute(parameters);
        model.addAttribute("movies", FilterMoviesResponse.of(result));
        return "movies";
    }

    @GetMapping("/{id}")
    public String movieDetail(@PathVariable UUID id, TimeZone timeZone, ModelMap model) {
        var movieId = new MovieId(id);
        var showtimeParameters = new ListAllFutureShowtimeDatesParameters(timeZone.toZoneId(), movieId);
        var statisticsParameters = new GetMovieStatisticsParameters(movieId);
        model.addAttribute("movieId", id);
        model.addAttribute("showtimes", listAllFutureShowtimeDates.execute(showtimeParameters));
        model.addAttribute("statistics", getMovieStatistics.execute(statisticsParameters));
        return "movie";
    }

    @GetMapping("/{id}/card")
    public String movieCard(@PathVariable UUID id, ModelMap model) {
        var parameters = new GetMovieInfoParameters(new MovieId(id));
        model.addAttribute("movie", MovieResponse.of(getMovieInfo.execute(parameters)));
        return "fragments/movie-horizontal-card";
    }

    @ResponseBody
    @GetMapping("/{id}/poster")
    public ResponseEntity<Resource> getPoster(@PathVariable UUID id) {
        var parameters = new GetMoviePosterParameters(new MovieId(id));
        Resource resource = getMoviePoster.execute(parameters);
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(resource);
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteMovie(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        deleteMovie.execute(new DeleteMovieParameters(new MovieId(id)));
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/movie";
    }

    @GetMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showRegisterMovie(ModelMap model) {
        List<GenreResponse> genres = listAllGenres.execute().stream().map(GenreResponse::of).toList();
        model.addAttribute("genres", genres);
        return "register-movie";
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String registerMovie(
        @RequestParam String title,
        @RequestParam String description,
        @RequestParam("genreId") List<UUID> genreIds,
        @RequestPart("poster") MultipartFile poster,
        @RequestParam String duration,
        RedirectAttributes redirectAttributes) {
        var parameters = new RegisterMovieParameters(
            title,
            description,
            genreIds.stream().map(GenreId::new).toList(),
            poster,
            Duration.parse(duration)
        );
        Movie movie = registerMovie.execute(parameters);
        redirectAttributes.addFlashAttribute("success", true);
        return  "redirect:/movie/" + movie.getId().getId();
    }
}
