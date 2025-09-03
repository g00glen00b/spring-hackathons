package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.usecase.ListMessagesInRoom;
import codes.dimitri.apps.chat.shared.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final ListMessagesInRoom listMessagesInRoom;

    @GetMapping("/message/fragment/list")
    public String listMessages(
        @RequestParam UUID roomId,
        @RequestParam(required = false) Instant before,
        @AuthenticationPrincipal SecurityUser currentUser,
        Model model) {
        var actualBefore = before == null ? Instant.now() : before;
        var parameters = new ListMessagesInRoom.Parameters(roomId, actualBefore);
        Page<Message> results = listMessagesInRoom.execute(parameters);
        List<MessageInfoResponse> responseList = results
            .map(result -> MessageInfoResponse.from(result, currentUser))
            .getContent();
        model.addAttribute("roomId", roomId);
        model.addAttribute("messages", responseList);
        return "fragments/message-list";
    }
}
