package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.usecase.ListMessagesInRoom;
import codes.dimitri.apps.chat.message.usecase.ReadMessage;
import codes.dimitri.apps.chat.shared.SecurityUser;
import codes.dimitri.apps.chat.shared.web.RoomWebSocketTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final ListMessagesInRoom listMessagesInRoom;
    private final ReadMessage readMessage;
    private final RoomWebSocketTemplate roomWebSocketTemplate;
    private final TemplateEngine templateEngine;

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

    @PostMapping("/message/{id}/read")
    @ResponseStatus(HttpStatus.CREATED)
    public void readMessage(@PathVariable UUID id, @AuthenticationPrincipal SecurityUser currentUser) {
        var parameters = new ReadMessage.Parameters(currentUser.id(), id);
        Message message = readMessage.execute(parameters);
        sendUpdatedViewCountToRoom(message);
    }

    private void sendUpdatedViewCountToRoom(Message message) {
        roomWebSocketTemplate.sendToRoom(message.getRoomId(), session -> {
            String payload = renderViewCount(message);
            return new TextMessage(payload);
        });
    }

    private String renderViewCount(Message message) {
        var parameters = Set.of("viewcount");
        var context = new Context();
        context.setVariable("messageId", message.getId());
        context.setVariable("viewCount", message.getReadUsers().size());
        return templateEngine.process("fragments/viewcount", parameters, context);
    }
}
