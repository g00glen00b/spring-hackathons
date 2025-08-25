package codes.dimitri.apps.leaderboard.game.usecase;

import codes.dimitri.apps.leaderboard.game.Game;
import codes.dimitri.apps.leaderboard.game.GameRepository;
import codes.dimitri.apps.leaderboard.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListGames {
    private final GameRepository repository;

    public List<Game> execute() {
        return repository.findAll(Sort.by("name"));
    }
}
