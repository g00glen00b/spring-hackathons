package codes.dimitri.apps.leaderboard.game;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class ScoreAuditId implements Serializable {
    @Column(name = "game_id")
    private UUID gameId;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "rev")
    private int revision;
}
