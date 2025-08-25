package codes.dimitri.apps.leaderboard.game;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@Table(name = "score_aud")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ScoreAudit {
    @EmbeddedId
    private ScoreAuditId id;
    private int score;
    @Column(name = "last_modified_at")
    private Instant lastModifiedAt;
}
