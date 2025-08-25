package codes.dimitri.apps.leaderboard.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private UUID id;
    private String username;
    private String password;

    public User(String username, String password) {
        this(UUID.randomUUID(), username, password);
    }
}
