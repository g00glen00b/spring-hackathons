package codes.dimitri.apps.chat.shared.web;

import org.springframework.web.socket.WebSocketMessage;

import java.util.UUID;
import java.util.function.Function;

public interface RoomWebSocketTemplate {
    void sendToAll(Function<SecuredWebSocketSession, WebSocketMessage<?>> mapper);
    void sendToRoom(UUID roomId, Function<SecuredWebSocketSession, WebSocketMessage<?>> mapper);
}
