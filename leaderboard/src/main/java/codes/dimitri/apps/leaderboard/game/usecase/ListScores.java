package codes.dimitri.apps.leaderboard.game.usecase;

import codes.dimitri.apps.leaderboard.game.ScoreAuditRepository;
import codes.dimitri.apps.leaderboard.game.ScoreView;
import codes.dimitri.apps.leaderboard.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.UUID;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListScores {
    private final ScoreAuditRepository repository;

    public record Parameters(UUID gameId, UUID userId, Instant lastModifiedBefore, Pageable pageable) {
        public Parameters {
            Assert.notNull(gameId, "gameId cannot be null");
            Assert.notNull(userId, "userId cannot be null");
            Assert.notNull(lastModifiedBefore, "lastModifiedBefore cannot be null");
            Assert.notNull(pageable, "pageable cannot be null");
            Assert.state(!lastModifiedBefore.isAfter(Instant.now()), "lastModifiedBefore cannot be in the future");
        }
    }

    public Page<ScoreView> execute(Parameters parameters) {
        return repository.findByGameIdAndLastModifiedAtBefore(parameters.gameId, parameters.userId, parameters.lastModifiedBefore, parameters.pageable);
    }
}
