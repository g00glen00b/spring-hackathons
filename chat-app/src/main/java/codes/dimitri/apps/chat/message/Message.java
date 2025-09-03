package codes.dimitri.apps.chat.message;

import codes.dimitri.apps.chat.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Message {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private UUID roomId;
    private String message;
    private Instant createdAt;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "message")
    private Set<MessageReadUser> readUsers;

    public Message(User user, UUID roomId, String message) {
        this(UUID.randomUUID(), user, roomId, message, Instant.now(), new HashSet<>());
    }

    public void addReadUser(User user) {
        MessageReadUser readUser = new MessageReadUser(new MessageReadUserId(id, user.getId()), this, user);
        readUsers.add(readUser);
    }
}
