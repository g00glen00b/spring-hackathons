package codes.dimitri.apps.broadcastserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        logger.info("Message from {}: {}", session.getId(), message.getPayload());
        for(WebSocketSession webSocketSession : sessions) {
            if (!session.equals(webSocketSession)) webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Connected: {}", session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Disconnected: {}", session.getId());
        sessions.remove(session);
    }
}
