package codes.dimitri.apps.chat.shared.web;

import codes.dimitri.apps.chat.shared.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Component
public class InMemoryRoomWebSocketTemplate implements RoomWebSocketTemplate {
    private final Map<UUID, List<SecuredWebSocketSession>> sessions = new HashMap<>();

    @Override
    public void sendToAll(Function<SecuredWebSocketSession, WebSocketMessage<?>> mapper) {
        sessions.keySet().forEach(roomId -> sendToRoom(roomId, mapper));
    }

    @Override
    public void sendToRoom(UUID roomId, Function<SecuredWebSocketSession, WebSocketMessage<?>> mapper) {
        sessions.get(roomId)
            .stream()
            .filter(SecuredWebSocketSession::isOpen)
            .forEach(session -> sendSafely(session, mapper));
    }

    private void sendSafely(SecuredWebSocketSession securedWebSocketSession, Function<SecuredWebSocketSession, WebSocketMessage<?>> mapper) {
        try {
            WebSocketMessage<?> message = mapper.apply(securedWebSocketSession);
            securedWebSocketSession.session().sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SecuredRoomWebSocketSession addSessionToRoom(WebSocketSession session) {
        UUID roomId = getRoomIdFromSession(session);
        var user = getUserFromSession(session).orElseThrow();
        var securedSession = new SecuredWebSocketSession(session, user);
        List<SecuredWebSocketSession> sessions = this.sessions.computeIfAbsent(roomId, id -> new ArrayList<>());
        sessions.add(securedSession);
        return new SecuredRoomWebSocketSession(securedSession, roomId);
    }

    public SecuredRoomWebSocketSession removeSessionFromRoom(WebSocketSession session) {
        UUID roomId = getRoomIdFromSession(session);
        List<SecuredWebSocketSession> sessions = this.sessions.get(roomId);
        SecuredWebSocketSession securedSession = sessions
            .stream()
            .filter(current -> session.equals(current.session()))
            .findAny()
            .orElseThrow();
        sessions.remove(securedSession);
        return new SecuredRoomWebSocketSession(securedSession, roomId);
    }

    private UUID getRoomIdFromSession(WebSocketSession session) {
        URI uri = Objects.requireNonNull(session.getUri());
        String[] parts = uri.toString().split("/");
        return UUID.fromString(parts[parts.length - 1]);
    }

    public Optional<SecurityUser> getUserFromSession(WebSocketSession session) {
        if (session.getPrincipal() instanceof UsernamePasswordAuthenticationToken token) {
            if (token.getPrincipal() instanceof SecurityUser user) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
