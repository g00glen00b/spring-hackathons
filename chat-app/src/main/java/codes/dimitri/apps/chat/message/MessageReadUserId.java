package codes.dimitri.apps.chat.message;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class MessageReadUserId implements Serializable {
    private UUID messageId;
    private UUID userId;
}
