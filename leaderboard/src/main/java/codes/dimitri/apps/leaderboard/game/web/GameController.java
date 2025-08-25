package codes.dimitri.apps.leaderboard.game.web;

import codes.dimitri.apps.leaderboard.game.ScoreView;
import codes.dimitri.apps.leaderboard.game.usecase.*;
import codes.dimitri.apps.leaderboard.shared.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final ListGames listGames;
    private final GetGame getGame;
    private final ListScores listScores;
    private final GetScore getScore;
    private final SubmitScore  submitScore;

    @GetMapping
    public String listGames(
        @RequestParam(required = false) LocalDate lastModifiedBefore,
        Model model) {
        var lastModifiedBeforeDefaulted = lastModifiedBefore == null ? LocalDate.now() : lastModifiedBefore;
        model.addAttribute("games", listGames.execute());
        model.addAttribute("lastModifiedBefore", lastModifiedBeforeDefaulted);
        model.addAttribute("today", LocalDate.now());
        return "games";
    }

    @GetMapping("/{id}")
    public String getGame(
        @PathVariable UUID id,
        @RequestParam(required = false) LocalDate lastModifiedBefore,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        Model model) {
        var lastModifiedBeforeDefaulted = lastModifiedBefore == null ? LocalDate.now() : lastModifiedBefore;
        var getGameParameters = new GetGame.Parameters(id);
        model.addAttribute("game", getGame.execute(getGameParameters));
        model.addAttribute("lastModifiedBefore", lastModifiedBeforeDefaulted);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        return "game";
    }

    @GetMapping("/{id}/score")
    public String listScores(
        @PathVariable UUID id,
        @RequestParam(required = false) LocalDate lastModifiedBefore,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "5") int size,
        @RequestParam(required = false) String paginationLink,
        @AuthenticationPrincipal SecurityUser user,
        TimeZone timeZone,
        Model model) {
        var pageRequest = PageRequest.of(page, size, Sort.by("score").descending());
        var lastModifiedBeforeDefaulted =  getLastModifiedBeforeDefaulted(lastModifiedBefore, timeZone);
        var parameters = new ListScores.Parameters(id, user.id(), lastModifiedBeforeDefaulted, pageRequest);
        Page<ScoreView> scores = listScores.execute(parameters);
        model.addAttribute("scores", scores);
        model.addAttribute("paginationLink", paginationLink);
        model.addAttribute("pages", getRenderedPages(scores));
        return "fragments/scores";
    }

    @GetMapping("/{id}/score/submit")
    public String getSubmitScore(@PathVariable UUID id, @AuthenticationPrincipal SecurityUser user,  Model model) {
        var getScoreParameters = new GetScore.Parameters(id, user.id());
        var getGameParameters = new GetGame.Parameters(id);
        model.addAttribute("score", getScore.execute(getScoreParameters));
        model.addAttribute("game", getGame.execute(getGameParameters));
        return "submit-score";
    }

    @PostMapping("/{id}/score/submit")
    public String submitScore(
        @PathVariable UUID id,
        @RequestParam int score,
        @AuthenticationPrincipal SecurityUser user,
        RedirectAttributes redirectAttributes) {
        var submitParameters = new SubmitScore.Parameters(user.id(), id, score);
        var getGameParameters = new GetGame.Parameters(id);
        submitScore.execute(submitParameters);
        var message = MessageFormat.format(
            "Successfully added score {0} for \"{1}\"",
            String.valueOf(score),
            getGame.execute(getGameParameters).getName()
        );
        redirectAttributes.addFlashAttribute("success", message);
        return "redirect:/game";
    }

    private static Instant getLastModifiedBeforeDefaulted(LocalDate lastModifiedBefore, TimeZone timeZone) {
        Instant currentTime = Instant.now();
        LocalDate currentDay = LocalDate.now();
        if (lastModifiedBefore == null || !lastModifiedBefore.isBefore(currentDay)) return currentTime;
        return atEndOfDay(lastModifiedBefore)
            .atZone(timeZone.toZoneId())
            .toInstant();
    }

    private static LocalDateTime atEndOfDay(LocalDate lastModifiedBefore) {
        return lastModifiedBefore
            .plusDays(1)
            .atStartOfDay()
            .minusSeconds(1);
    }

    private static List<PageLink> getRenderedPages(Page<?> page) {
        var pages  = new ArrayList<PageLink>();
        pages.add(new PageLink("First", page.hasPrevious() ? 0 : null, true));
        pages.add(new PageLink("Previous", page.hasPrevious() ? page.getNumber() - 1 : null, true));
        if (page.getNumber() > 2) pages.add(new PageLink("...", null, true));
        IntStream
            .rangeClosed(-2, +2)
            .map(offset -> page.getNumber() + offset)
            .filter(pageNumber -> pageNumber >= 0)
            .filter(pageNumber -> pageNumber < page.getTotalPages())
            .mapToObj(pageNumber -> new PageLink(Integer.toString(pageNumber + 1), pageNumber, false))
            .forEach(pages::add);
        if (page.getTotalPages() - page.getNumber() > 2) pages.add(new PageLink("...", null, true));
        pages.add(new PageLink("Next", page.hasNext() ? page.getNumber() + 1 : null, true));
        pages.add(new PageLink("Last", page.hasNext() ? page.getTotalPages() - 1 : null, true));
        return pages;
    }
}
