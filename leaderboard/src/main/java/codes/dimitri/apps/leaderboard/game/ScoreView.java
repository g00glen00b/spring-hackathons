package codes.dimitri.apps.leaderboard.game;

import java.time.Instant;
import java.util.UUID;

public record ScoreView(UUID gameView, UUID userId, String username, int score, Instant lastModifiedAt, boolean currentUser) {
}
