package codes.dimitri.apps.chat.message;

import codes.dimitri.apps.chat.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MessageReadUser {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private MessageReadUserId id;
    @MapsId("messageId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Message message;
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
