package codes.dimitri.apps.chat.user.web;

import codes.dimitri.apps.chat.shared.web.RoomWebSocketTemplate;
import codes.dimitri.apps.chat.shared.web.SecuredRoomWebSocketSession;
import codes.dimitri.apps.chat.user.usecase.ListUsers;
import codes.dimitri.apps.chat.user.usecase.UpdateUserOnlineState;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public final class UserOnlineWebSocketHandler extends AbstractUserListWebSocketHandler {
    private final UpdateUserOnlineState updateUserOnlineState;

    public UserOnlineWebSocketHandler(ListUsers listUsers, RoomWebSocketTemplate roomWebSocketTemplate, TemplateEngine templateEngine, UpdateUserOnlineState updateUserOnlineState) {
        super(listUsers, roomWebSocketTemplate, templateEngine);
        this.updateUserOnlineState = updateUserOnlineState;
    }

    @Override
    public void onConnect(SecuredRoomWebSocketSession session) {
        var parameters = new UpdateUserOnlineState.Parameters(session.session().user().id(), true);
        updateUserOnlineState.execute(parameters);
        sendUserListToAll(session.session().user());
    }

    @Override
    public void onDisconnect(SecuredRoomWebSocketSession session) {
        var parameters = new UpdateUserOnlineState.Parameters(session.session().user().id(), false);
        updateUserOnlineState.execute(parameters);
        sendUserListToAll(session.session().user());
    }
}
