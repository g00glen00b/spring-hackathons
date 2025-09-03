package codes.dimitri.apps.chat.shared;

import codes.dimitri.apps.chat.shared.web.WebSocketRequestWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
class WebSocketConfiguration implements WebSocketConfigurer {
    private final WebSocketRequestWebSocketHandler webSocketRequestWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketRequestWebSocketHandler, "/ws/room/**");
    }
}
