package codes.dimitri.apps.chat.shared.web;

import java.util.UUID;

public record SecuredRoomWebSocketSession(SecuredWebSocketSession session, UUID roomId) {
}
