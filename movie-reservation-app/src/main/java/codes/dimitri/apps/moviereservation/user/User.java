package codes.dimitri.apps.moviereservation.user;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"user\"")
public class User {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private UserId id;
    private String username;
    private String password;
    private boolean admin;

    public User(UserId id, String username, String password) {
        this(id, username, password, false);
    }
}
