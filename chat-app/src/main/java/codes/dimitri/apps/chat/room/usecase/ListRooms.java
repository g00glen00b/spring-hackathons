package codes.dimitri.apps.chat.room.usecase;

import codes.dimitri.apps.chat.room.Room;
import codes.dimitri.apps.chat.room.RoomRepository;
import codes.dimitri.apps.chat.shared.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListRooms {
    private final RoomRepository repository;

    public List<Room> execute() {
        return repository.findAll();
    }
}
