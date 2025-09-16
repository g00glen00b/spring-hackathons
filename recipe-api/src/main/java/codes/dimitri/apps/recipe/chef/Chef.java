package codes.dimitri.apps.recipe.chef;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Chef {
    @Id
    private UUID id;
    private String username;
    private String displayname;
    private String password;

    public Chef(String username, String displayname, String password) {
        this(UUID.randomUUID(), username, displayname, password);
    }
}
