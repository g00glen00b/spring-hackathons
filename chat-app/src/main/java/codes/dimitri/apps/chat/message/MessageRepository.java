package codes.dimitri.apps.chat.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    @Query("select m from Message m where m.roomId = ?1 and m.createdAt < ?2")
    Page<Message> findAllByRoomIdAndCreatedAtBefore(UUID roomId, Instant before, Pageable pageable);
}
