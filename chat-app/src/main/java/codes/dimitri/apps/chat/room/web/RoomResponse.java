package codes.dimitri.apps.chat.room.web;

import codes.dimitri.apps.chat.room.Room;

import java.util.UUID;

public record RoomResponse(UUID id, String name, boolean active) {
    public static RoomResponse from(Room room, UUID activeId) {
        return new RoomResponse(room.getId(), room.getName(), activeId.equals(room.getId()));
    }
}
