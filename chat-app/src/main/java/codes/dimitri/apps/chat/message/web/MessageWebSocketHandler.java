package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.usecase.SendMessageToRoom;
import codes.dimitri.apps.chat.shared.SecurityUser;
import codes.dimitri.apps.chat.user.usecase.ListUsers;
import codes.dimitri.apps.chat.user.usecase.UpdateUserOnlineState;
import codes.dimitri.apps.chat.user.usecase.UpdateUserTypingState;
import codes.dimitri.apps.chat.user.web.UserInfoResponse;
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
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageWebSocketHandler implements WebSocketHandler {
    private final Map<UUID, List<WebSocketSession>> sessions = new HashMap<>();
    private final UpdateUserOnlineState updateUserOnlineState;
    private final UpdateUserTypingState updateUserTypingState;
    private final SendMessageToRoom sendMessageToRoom;
    private final ListUsers listUsers;
    private final ObjectMapper objectMapper;
    private final TemplateEngine templateEngine;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        addSession(session);
        SecurityUser currentUser = getUserFromSession(session).orElseThrow();
        var parameters = new UpdateUserOnlineState.Parameters(currentUser.id(), true);
        updateUserOnlineState.execute(parameters);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        removeSession(session);
        SecurityUser currentUser = getUserFromSession(session).orElseThrow();
        var parameters = new UpdateUserOnlineState.Parameters(currentUser.id(), false);
        updateUserOnlineState.execute(parameters);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        SecurityUser currentUser = getUserFromSession(session).orElseThrow();
        WebSocketRequest request = mapFromMessage(message).orElseThrow();
        log.info("Received WebSocket Message: {}", request);
        if (request.isSubmit()) {
            setUserTyping(currentUser, false);
            sendMessageToRoom(request, currentUser);
            sendUserListToAll(currentUser);
        } else if (request.isTyping()) {
            setUserTyping(currentUser, true);
            sendUserListToAll(currentUser);
        }
    }

    private void setUserTyping(SecurityUser currentUser, boolean typing) {
        var parameters = new UpdateUserTypingState.Parameters(currentUser.id(), typing);
        updateUserTypingState.execute(parameters);
    }

    private void sendMessageToRoom(WebSocketRequest request, SecurityUser currentUser) {
        var sendMessageToRoomParameters = request.toParameters(currentUser.id());
        Message message = sendMessageToRoom.execute(sendMessageToRoomParameters);
        sendToRoom(message.getRoomId(), session -> {
            var user = getUserFromSession(session).orElseThrow();
            String payload = renderMessage(message, user);
            return new TextMessage(payload);
        });
    }

    private void sendUserListToAll(SecurityUser currentUser) {
        var payload = renderUserList(currentUser);
        var message = new TextMessage(payload);
        sendToAll(session -> message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
    }

    private static Optional<SecurityUser> getUserFromSession(WebSocketSession session) {
        if (session.getPrincipal() instanceof UsernamePasswordAuthenticationToken token) {
            if (token.getPrincipal() instanceof SecurityUser user) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private Optional<WebSocketRequest> mapFromMessage(WebSocketMessage<?> message) throws JsonProcessingException {
        if (message instanceof TextMessage textMessage) {
            return Optional.of(objectMapper.readValue(textMessage.getPayload(), WebSocketRequest.class));
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

    private String renderUserList(SecurityUser currentUser) {
        List<UserInfoResponse> users = listUsers.execute().stream().map(user -> UserInfoResponse.from(user, currentUser)).toList();
        var parameters = Set.of("user-list");
        var context = new Context();
        context.setVariable("users", users);
        return templateEngine.process("fragments/user-list", parameters, context);
    }

    private void sendToAll(Function<WebSocketSession, WebSocketMessage<?>> mapper) {
        sessions.keySet().forEach(roomId -> sendToRoom(roomId, mapper));
    }

    private void sendToRoom(UUID roomId, Function<WebSocketSession, WebSocketMessage<?>> mapper) {
        sessions.get(roomId)
            .stream()
            .filter(WebSocketSession::isOpen)
            .forEach(session -> sendSafely(session, mapper));
    }

    private void sendSafely(WebSocketSession session, Function<WebSocketSession, WebSocketMessage<?>> mapper) {
        try {
            session.sendMessage(mapper.apply(session));
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
