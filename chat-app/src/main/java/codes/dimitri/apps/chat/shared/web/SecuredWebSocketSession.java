package codes.dimitri.apps.chat.shared.web;

import codes.dimitri.apps.chat.shared.SecurityUser;
import org.springframework.web.socket.WebSocketSession;

public record SecuredWebSocketSession(WebSocketSession session, SecurityUser user) {
    public boolean isOpen() {
        return session.isOpen();
    }
}
