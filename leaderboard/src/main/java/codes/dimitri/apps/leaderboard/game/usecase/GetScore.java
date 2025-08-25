package codes.dimitri.apps.leaderboard.game.usecase;

import codes.dimitri.apps.leaderboard.game.Score;
import codes.dimitri.apps.leaderboard.game.ScoreId;
import codes.dimitri.apps.leaderboard.game.ScoreRepository;
import codes.dimitri.apps.leaderboard.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class GetScore {
    private final ScoreRepository repository;

    public record Parameters(UUID gameId, UUID userId) {
        public Parameters {
            Assert.notNull(gameId, "Game ID must not be null");
            Assert.notNull(userId, "User ID must not be null");
        }
    }

    public int execute(Parameters parameters) {
        return repository
            .findById(new ScoreId(parameters.gameId, parameters.userId))
            .map(Score::getScore)
            .orElse(0);
    }
}
