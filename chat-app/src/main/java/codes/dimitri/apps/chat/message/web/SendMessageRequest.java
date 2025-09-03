package codes.dimitri.apps.chat.message.web;

import codes.dimitri.apps.chat.message.usecase.SendMessageToRoom;

import java.util.UUID;

public record SendMessageRequest(UUID roomId, String message) {
    public SendMessageToRoom.Parameters toParameters(UUID userId) {
        return new SendMessageToRoom.Parameters(
            roomId,
            userId,message
        );
    }
}
