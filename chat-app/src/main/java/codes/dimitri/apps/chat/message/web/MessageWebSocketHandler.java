package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.usecase.SendMessageToRoom;
import codes.dimitri.apps.chat.shared.SecurityUser;
import codes.dimitri.apps.chat.user.usecase.UpdateUserOnlineState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageWebSocketHandler implements WebSocketHandler {
    private final Map<UUID, List<WebSocketSession>> sessions = new HashMap<>();
    private final UpdateUserOnlineState updateUserOnlineState;
    private final SendMessageToRoom sendMessageToRoom;
    private final ObjectMapper objectMapper;
    private final TemplateEngine templateEngine;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        addSession(session);
        SecurityUser currentUser = userFromSession(session).orElseThrow();
        var parameters = new UpdateUserOnlineState.Parameters(currentUser.id(), true);
        updateUserOnlineState.execute(parameters);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        removeSession(session);
        SecurityUser currentUser = userFromSession(session).orElseThrow();
        var parameters = new UpdateUserOnlineState.Parameters(currentUser.id(), false);
        updateUserOnlineState.execute(parameters);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        SecurityUser currentUser = userFromSession(session).orElseThrow();
        SendMessageRequest request = mapFromMessage(message).orElseThrow();
        var parameters = request.toParameters(currentUser.id());
        Message result = sendMessageToRoom.execute(parameters);
        sendToRoom(result.getRoomId(), result);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
    }

    private static Optional<SecurityUser> userFromSession(WebSocketSession session) {
        if (session.getPrincipal() instanceof UsernamePasswordAuthenticationToken token) {
            if (token.getPrincipal() instanceof SecurityUser user) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private Optional<SendMessageRequest> mapFromMessage(WebSocketMessage<?> message) throws JsonProcessingException {
        if (message instanceof TextMessage textMessage) {
            return Optional.of(objectMapper.readValue(textMessage.getPayload(), SendMessageRequest.class));
        } else {
            return Optional.empty();
        }
    }

    private String renderMessage(Message message, SecurityUser currentUser) {
        var parameters = Set.of("message-wrapper");
        var context = new Context();
        context.setVariable("message", MessageInfoResponse.from(message, currentUser));
        return templateEngine.process("fragments/message-wrapper", parameters, context);
    }

    private void sendToRoom(UUID roomId, Message message) {
        sessions.get(roomId)
            .stream()
            .filter(WebSocketSession::isOpen)
            .forEach(session -> sendSafely(session, message));
    }

    private void sendSafely(WebSocketSession session, Message message)  {
        try {
            SecurityUser sessionUser = userFromSession(session).orElseThrow();
            String payload = renderMessage(message, sessionUser);
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSession(WebSocketSession session) {
        UUID roomId = getRoomIdFromSession(session);
        List<WebSocketSession> sessions = this.sessions.computeIfAbsent(roomId, id -> new ArrayList<>());
        sessions.add(session);
    }

    private void removeSession(WebSocketSession session) {
        UUID roomId = getRoomIdFromSession(session);
        List<WebSocketSession> sessions = this.sessions.get(roomId);
        sessions.remove(session);
    }

    private UUID getRoomIdFromSession(WebSocketSession session) {
        URI uri = Objects.requireNonNull(session.getUri());
        String[] parts = uri.toString().split("/");
        return UUID.fromString(parts[parts.length - 1]);
    }
}
