package codes.dimitri.apps.moviereservation.movie.web;

import codes.dimitri.apps.moviereservation.movie.Movie;
import codes.dimitri.apps.moviereservation.movie.MovieId;
import codes.dimitri.apps.moviereservation.movie.Showtime;
import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import codes.dimitri.apps.moviereservation.movie.usecase.*;
import codes.dimitri.apps.moviereservation.theatre.TheatreId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class ShowtimeController {
    private final GetShowtimeMovieInfo getShowtimeMovieInfo;
    private final FilterShowtimes filterShowtimes;
    private final AddShowtimeToMovie addShowtimeToMovie;
    private final DeleteShowtime deleteShowtime;

    @GetMapping("/showtime/{showtimeId}/card")
    public String showtimeMovieCard(@PathVariable UUID showtimeId, ModelMap model) {
        var movieInfoParameters = new GetShowtimeMovieInfoParameters(new ShowtimeId(showtimeId));
        model.addAttribute("movie", MovieResponse.of(getShowtimeMovieInfo.execute(movieInfoParameters)));
        return "fragments/movie-horizontal-card";
    }

    @GetMapping("/{id}/showtime/{date}")
    public String showtimeDateDetail(@PathVariable UUID id, @PathVariable LocalDate date, TimeZone timeZone, ModelMap model) {
        MovieId movieId = new MovieId(id);
        var showtimeParameters = new FilterShowtimesParameters(timeZone.toZoneId(), movieId, date);
        model.addAttribute("date", date);
        model.addAttribute("movieId", id);
        model.addAttribute("showtimes", filterShowtimes.execute(showtimeParameters).stream().map(ShowtimeResponse::of).toList());
        return "showtimes";
    }

    @GetMapping("/{id}/showtime/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddShowtime(@PathVariable UUID id, ModelMap model) {
        model.addAttribute("movieId", id);
        return "add-showtime";
    }

    @PostMapping("/{id}/showtime")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addShowtime(
        @PathVariable UUID id,
        @RequestParam UUID theatreId,
        @RequestParam LocalDateTime date,
        @RequestParam BigDecimal price,
        TimeZone timeZone,
        ModelMap model,
        RedirectAttributes redirectAttributes) {
        var parameters = new AddShowtimeToMovieParameters(
            new MovieId(id),
            new TheatreId(theatreId),
            date.atZone(timeZone.toZoneId()).toInstant(),
            price
        );
        try {
            Showtime showtime = addShowtimeToMovie.execute(parameters);
            LocalDateTime startingAt = showtime.getLocalStartingAt(timeZone.toZoneId());
            redirectAttributes.addFlashAttribute("added", true);
            return "redirect:/movie/" + id + "/showtime/" + startingAt.toLocalDate();
        } catch (Throwable ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("date", date);
            model.addAttribute("price", price);
            return "add-showtime";
        }
    }

    @PostMapping("/showtime/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteShowtime(
        @PathVariable UUID id,
        RedirectAttributes redirectAttributes) {
        ShowtimeId showtimeId = new ShowtimeId(id);
        var getParameters = new GetShowtimeMovieInfoParameters(showtimeId);
        var parameters = new DeleteShowtimeParameters(showtimeId);
        Movie movie = getShowtimeMovieInfo.execute(getParameters);
        deleteShowtime.execute(parameters);
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/movie/" + movie.getId().getId();
    }
}
