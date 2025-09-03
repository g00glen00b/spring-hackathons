package codes.dimitri.apps.chat.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private UUID id;
    private String username;
    private String password;
    @Setter
    private boolean online;
    @Setter
    private boolean typing;

    public User(String username, String password) {
        this(UUID.randomUUID(), username, password, false, false);
    }
}
