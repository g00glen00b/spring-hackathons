package codes.dimitri.apps.chat.user.web;

import codes.dimitri.apps.chat.shared.web.RoomWebSocketTemplate;
import codes.dimitri.apps.chat.shared.web.SecuredRoomWebSocketSession;
import codes.dimitri.apps.chat.shared.web.WebSocketRequest;
import codes.dimitri.apps.chat.user.usecase.ListUsers;
import codes.dimitri.apps.chat.user.usecase.UpdateUserTypingState;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public final class UserTypingWebSocketHandler extends AbstractUserListWebSocketHandler {
    private final UpdateUserTypingState updateUserTypingState;

    public UserTypingWebSocketHandler(ListUsers listUsers, RoomWebSocketTemplate roomWebSocketTemplate, TemplateEngine templateEngine, UpdateUserTypingState updateUserTypingState) {
        super(listUsers, roomWebSocketTemplate, templateEngine);
        this.updateUserTypingState = updateUserTypingState;
    }

    @Override
    public void onMessage(WebSocketRequest request, SecuredRoomWebSocketSession session) {
        if (request.isTyping()) {
            var parameters = new UpdateUserTypingState.Parameters(session.session().user().id(), !request.message().isEmpty());
            updateUserTypingState.execute(parameters);
            sendUserListToAll(session.session().user());
        }
    }
}
