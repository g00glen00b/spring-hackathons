package codes.dimitri.apps.leaderboard.game.usecase;

import codes.dimitri.apps.leaderboard.game.Game;
import codes.dimitri.apps.leaderboard.game.GameRepository;
import codes.dimitri.apps.leaderboard.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class GetGame {
    private final GameRepository repository;

    public record Parameters(UUID id) {
        public Parameters {
            Assert.notNull(id, "Game ID must not be null");
        }
    }

    public Game execute(Parameters parameters) {
        return repository.findById(parameters.id).orElseThrow();
    }
}
