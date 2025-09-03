package codes.dimitri.apps.chat.shared.web;

public interface WebSocketRequestHandler {
    default void onMessage(WebSocketRequest request, SecuredRoomWebSocketSession session) {}
    default void onConnect(SecuredRoomWebSocketSession session) {}
    default void onDisconnect(SecuredRoomWebSocketSession session) {}
}
