package codes.dimitri.apps.leaderboard.game;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Score {
    @EmbeddedId
    private ScoreId id;
    @Setter
    private int score;
    @LastModifiedDate
    private Instant lastModifiedAt;

    public Score(ScoreId id, int score) {
        this(id, score, null);
    }
}
