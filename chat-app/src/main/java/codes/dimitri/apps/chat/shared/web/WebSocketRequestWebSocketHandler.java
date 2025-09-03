package codes.dimitri.apps.chat.shared.web;

import codes.dimitri.apps.chat.shared.SecurityUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketRequestWebSocketHandler implements WebSocketHandler {
    private final InMemoryRoomWebSocketTemplate inMemoryWebSocketTemplate;
    private final List<WebSocketRequestHandler> handlers;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        SecuredRoomWebSocketSession securedSession = inMemoryWebSocketTemplate.addSessionToRoom(session);
        handlers.forEach(handler -> handler.onConnect(securedSession));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        SecuredRoomWebSocketSession securedSession = inMemoryWebSocketTemplate.removeSessionFromRoom(session);
        handlers.forEach(handler -> handler.onDisconnect(securedSession));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        WebSocketRequest request = mapRequestFromMessage(message).orElseThrow();
        SecurityUser user = inMemoryWebSocketTemplate.getUserFromSession(session).orElseThrow();
        log.info("Handling message {} for user {}", request, user.getUsername());
        handlers.forEach(handler -> handler.onMessage(request, new SecuredRoomWebSocketSession(
            new SecuredWebSocketSession(session, user),
            request.roomId()
        )));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
    }

    private Optional<WebSocketRequest> mapRequestFromMessage(WebSocketMessage<?> message) throws JsonProcessingException {
        if (message instanceof TextMessage textMessage) {
            return Optional.of(objectMapper.readValue(textMessage.getPayload(), WebSocketRequest.class));
        } else {
            return Optional.empty();
        }
    }
}
