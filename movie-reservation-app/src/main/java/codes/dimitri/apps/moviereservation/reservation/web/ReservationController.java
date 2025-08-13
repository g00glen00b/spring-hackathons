package codes.dimitri.apps.moviereservation.reservation.web;

import codes.dimitri.apps.moviereservation.core.implementation.SecurityUser;
import codes.dimitri.apps.moviereservation.movie.MovieId;
import codes.dimitri.apps.moviereservation.movie.ShowtimeId;
import codes.dimitri.apps.moviereservation.reservation.ReservationId;
import codes.dimitri.apps.moviereservation.reservation.SeatReservationState;
import codes.dimitri.apps.moviereservation.reservation.usecase.*;
import codes.dimitri.apps.moviereservation.theatre.SeatId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ListAllReservedShowtimeSeats listAllReservedShowtimeSeats;
    private final NavigateReservation navigateReservation;
    private final MakeReservation makeReservation;
    private final ListAllFutureUserReservations listAllFutureUserReservations;
    private final CancelReservation cancelReservation;

    @GetMapping("/draft/{showtimeId}")
    public String reserveSeats(@PathVariable UUID showtimeId, ModelMap model) {
        var parameters = new ListAllReservedShowtimeSeatsParameters(new ShowtimeId(showtimeId));
        List<SeatReservationState> seatReservationStates = listAllReservedShowtimeSeats.execute(parameters);
        model.addAttribute("showtimeId", showtimeId);
        model.addAttribute("seats", seatReservationStates.stream().map(SeatReservationStateResponse::of).toList());
        return "reservation";
    }

    @PostMapping("/draft/{showtimeId}")
    public String makeReservation(
        @PathVariable UUID showtimeId,
        @RequestParam("seatId") List<UUID> seatIds,
        @AuthenticationPrincipal SecurityUser user,
        RedirectAttributes redirectAttributes) {
        var actualSeatIds = seatIds.stream().map(SeatId::new).toList();
        var parameters = new MakeReservationParameters(user.getId(), new ShowtimeId(showtimeId), actualSeatIds);
        makeReservation.execute(parameters);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/reservation";
    }

    @GetMapping("/navigation")
    public String getNavigation(
        @RequestParam(required = false) UUID movieId,
        @RequestParam(required = false) LocalDate date,
        @RequestParam(required = false) UUID showtimeId,
        TimeZone timeZone,
        ModelMap model) {
        var parameters = new NavigateReservationParameters(
            movieId == null ? null : new MovieId(movieId),
            date,
            showtimeId == null ? null : new ShowtimeId(showtimeId),
            timeZone.toZoneId()
        );
        model.addAttribute("state", navigateReservation.execute(parameters));
        return "fragments/reservation-breadcrumb";
    }

    @GetMapping
    public String getReservations(@AuthenticationPrincipal SecurityUser user, ModelMap model) {
        var parameters = new ListAllFutureUserReservationsParameters(user.getId());
        List<ReservationShowtimeTheatreTuple> results = listAllFutureUserReservations.execute(parameters);
        model.addAttribute("reservations", results.stream().map(ReservationResponse::of).toList());
        return "reservations";
    }

    @PostMapping("/{id}/cancel")
    public String cancelReservation(
        @PathVariable UUID id,
        @AuthenticationPrincipal SecurityUser user,
        RedirectAttributes redirectAttributes) {
        var parameters = new CancelReservationParameters(new ReservationId(id), user.getId());
        cancelReservation.execute(parameters);
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/reservation";
    }
}
