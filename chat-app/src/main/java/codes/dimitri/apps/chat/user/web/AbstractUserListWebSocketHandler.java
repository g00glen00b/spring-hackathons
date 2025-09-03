package codes.dimitri.apps.chat.user.web;

import codes.dimitri.apps.chat.shared.SecurityUser;
import codes.dimitri.apps.chat.shared.web.WebSocketRequestHandler;
import codes.dimitri.apps.chat.shared.web.RoomWebSocketTemplate;
import codes.dimitri.apps.chat.user.usecase.ListUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
sealed class AbstractUserListWebSocketHandler implements WebSocketRequestHandler permits UserOnlineWebSocketHandler, UserTypingWebSocketHandler {
    private final ListUsers listUsers;
    private final RoomWebSocketTemplate roomWebSocketTemplate;
    private final TemplateEngine templateEngine;

    void sendUserListToAll(SecurityUser currentUser) {
        var payload = renderUserList(currentUser);
        var message = new TextMessage(payload);
        roomWebSocketTemplate.sendToAll(session -> message);
    }

    String renderUserList(SecurityUser currentUser) {
        List<UserInfoResponse> users = listUsers.execute().stream().map(user -> UserInfoResponse.from(user, currentUser)).toList();
        var parameters = Set.of("user-list");
        var context = new Context();
        context.setVariable("users", users);
        return templateEngine.process("fragments/user-list", parameters, context);
    }
}
