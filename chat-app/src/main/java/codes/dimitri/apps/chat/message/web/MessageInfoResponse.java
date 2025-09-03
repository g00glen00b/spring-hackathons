package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.shared.SecurityUser;

import java.time.Instant;
import java.util.UUID;

public record MessageInfoResponse(UUID id, String message, Instant createdAt, String username, boolean isCurrentUser) {
    public static MessageInfoResponse from(Message message, SecurityUser currentUser) {
        return new MessageInfoResponse(
            message.getId(),
            message.getMessage(),
            message.getCreatedAt(),
            message.getUser().getUsername(),
            currentUser.isSameUser(message.getUser())
        );
    }
}
