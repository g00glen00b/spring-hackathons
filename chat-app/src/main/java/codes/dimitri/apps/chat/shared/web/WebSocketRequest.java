package codes.dimitri.apps.chat.shared.web;

import codes.dimitri.apps.chat.message.usecase.AddMessageToRoom;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record WebSocketRequest(UUID roomId, String message, @JsonProperty("HEADERS") Headers headers) {
    public AddMessageToRoom.Parameters toParameters(UUID userId) {
        return new AddMessageToRoom.Parameters(
            roomId,
            userId,message
        );
    }

    public boolean isSubmit() {
        return headers.trigger == null && !message.isEmpty();
    }

    public boolean isTyping() {
        return "message".equals(headers.trigger);
    }

    public record Headers(@JsonProperty("HX-Trigger") String trigger) {}
}
