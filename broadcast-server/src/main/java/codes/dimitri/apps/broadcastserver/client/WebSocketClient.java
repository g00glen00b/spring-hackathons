package codes.dimitri.apps.broadcastserver.client;

import codes.dimitri.apps.broadcastserver.shared.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Component
@Profile("client")
@ClientEndpoint
public class WebSocketClient {
    private final ObjectMapper objectMapper;
    private final String username;
    private Session session;

    public WebSocketClient(ObjectMapper objectMapper, @Value("${username:}") String username) {
        this.objectMapper = objectMapper;
        this.username = Objects.requireNonNull(username, "Provide a username with the --username flag");
    }

    @OnOpen
    public void onOpen() {
        System.out.println("🚀 Connected to server");
    }

    @OnMessage
    public void onMessage(String message) throws JsonProcessingException {
        ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
        System.out.println(chatMessage.username() + ": " + chatMessage.message());
    }

    @PostConstruct
    void connect() throws DeploymentException, IOException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, URI.create("ws://localhost:8080/ws"));
    }

    public void send(String message) throws JsonProcessingException {
        Objects.requireNonNull(session, "No open session yet");
        String payload = objectMapper.writeValueAsString(new ChatMessage(username, message));
        session.getAsyncRemote().sendText(payload);
    }
}
