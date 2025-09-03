package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.shared.SecurityUser;

import java.time.Instant;

public record MessageInfoResponse(String message, Instant createdAt, String username, boolean isCurrentUser) {
    public static MessageInfoResponse from(Message message, SecurityUser currentUser) {
        return new MessageInfoResponse(
            message.getMessage(),
            message.getCreatedAt(),
            message.getUser().getUsername(),
            currentUser.isSameUser(message.getUser())
        );
    }
}
