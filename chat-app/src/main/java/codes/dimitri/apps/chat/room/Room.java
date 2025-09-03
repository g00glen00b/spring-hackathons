package codes.dimitri.apps.chat.room;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Room {
    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
}
