package codes.dimitri.apps.moviereservation.movie.usecase;

import java.math.BigDecimal;

public record MovieStatistics(int ticketsSold, BigDecimal totalRevenue) {
}
