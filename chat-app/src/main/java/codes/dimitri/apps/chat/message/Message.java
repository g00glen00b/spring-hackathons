package codes.dimitri.apps.chat.message;

import codes.dimitri.apps.chat.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Message {
    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private UUID roomId;
    private String message;
    private Instant createdAt;

    public Message(User user, UUID roomId, String message) {
        this(UUID.randomUUID(), user, roomId, message, Instant.now());
    }
}
