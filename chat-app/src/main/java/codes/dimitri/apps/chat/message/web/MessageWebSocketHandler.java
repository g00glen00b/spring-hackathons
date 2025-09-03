package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.usecase.AddMessageToRoom;
import codes.dimitri.apps.chat.shared.SecurityUser;
import codes.dimitri.apps.chat.shared.web.RoomWebSocketTemplate;
import codes.dimitri.apps.chat.shared.web.SecuredRoomWebSocketSession;
import codes.dimitri.apps.chat.shared.web.WebSocketRequest;
import codes.dimitri.apps.chat.shared.web.WebSocketRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MessageWebSocketHandler implements WebSocketRequestHandler {
    private final RoomWebSocketTemplate roomWebSocketTemplate;
    private final AddMessageToRoom addMessageToRoom;
    private final TemplateEngine templateEngine;

    @Override
    public void onMessage(WebSocketRequest request, SecuredRoomWebSocketSession session) {
        if (request.isSubmit()) {
            var parameters = request.toParameters(session.session().user().id());
            Message message = addMessageToRoom.execute(parameters);
            roomWebSocketTemplate.sendToRoom(session.roomId(), receiverSession -> {
                String payload = renderMessage(message, receiverSession.user());
                return new TextMessage(payload);
            });
        }
    }

    private String renderMessage(Message message, SecurityUser currentUser) {
        var parameters = Set.of("message-wrapper");
        var context = new Context();
        context.setVariable("message", MessageInfoResponse.from(message, currentUser));
        return templateEngine.process("fragments/message-wrapper", parameters, context);
    }

}
