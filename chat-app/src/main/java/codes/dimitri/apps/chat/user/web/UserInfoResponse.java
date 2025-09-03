package codes.dimitri.apps.chat.user.web;

import codes.dimitri.apps.chat.shared.SecurityUser;
import codes.dimitri.apps.chat.user.User;

public record UserInfoResponse(String username, boolean online, boolean typing) {
    public static UserInfoResponse from(User user, SecurityUser currentUser) {
        // Overrule the online state for the current user
        // This allows the user to appear as online even though the WebSocket connection is still in progress
        boolean onlineState = user.isOnline() || currentUser.isSameUser(user);
        return new UserInfoResponse(user.getUsername(), onlineState, user.isTyping());
    }
}
