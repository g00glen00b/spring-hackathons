package codes.dimitri.apps.leaderboard.game;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class ScoreId implements Serializable {
    private UUID gameId;
    private UUID userId;
}
