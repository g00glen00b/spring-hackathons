package codes.dimitri.apps.leaderboard.game;

import codes.dimitri.apps.leaderboard.shared.ReadOnlyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.UUID;

public interface ScoreAuditRepository extends ReadOnlyRepository<ScoreAudit, ScoreAuditId> {
    @Query("""
        select new codes.dimitri.apps.leaderboard.game.ScoreView(
            s.id.gameId,
            s.id.userId,
            u.username,
            s.score,
            s.lastModifiedAt,
            case when cu.id = u.id then true else false end
        )
        from ScoreAudit s, User u, User cu
        where s.id.gameId = ?1
        and s.id.userId = u.id
        and cu.id = ?2
        and s.lastModifiedAt = (
            select max(s2.lastModifiedAt)
            from ScoreAudit s2
            where s2.id.gameId = s.id.gameId
            and s2.id.userId = s.id.userId
            and s2.lastModifiedAt <= ?3
        )
    """)
    Page<ScoreView> findByGameIdAndLastModifiedAtBefore(UUID gameId, UUID userId, Instant lastModifiedBefore, Pageable pageable);
}
