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
public class SubmitScore {
    private final ScoreRepository repository;

    public record Parameters(UUID userId, UUID gameId, int score) {
        public Parameters {
            Assert.notNull(userId, "userId cannot be null");
            Assert.notNull(gameId, "gameId cannot be null");
            Assert.state(score >= 0, "score cannot be negative");
        }
    }

    public Score execute(Parameters parameters) {
        var id = new ScoreId(parameters.userId, parameters.gameId);
        return repository
            .findById(id)
            .map(score -> update(score, parameters))
            .orElseGet(() -> create(parameters));
    }

    private Score create(Parameters parameters) {
        return repository.save(new Score(
            new ScoreId(parameters.gameId, parameters.userId),
            parameters.score
        ));
    }

    private Score update(Score score, Parameters parameters) {
        score.setScore(parameters.score);
        return score;
    }
}
