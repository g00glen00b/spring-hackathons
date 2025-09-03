package codes.dimitri.apps.chat.room.usecase;

import codes.dimitri.apps.chat.room.Room;
import codes.dimitri.apps.chat.room.RoomRepository;
import codes.dimitri.apps.chat.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class GetRoomInfo {
    private final RoomRepository repository;

    public record Parameters(UUID id) {
        public Parameters {
            Assert.notNull(id, "id must not be null");
        }
    }

    public Room execute(Parameters parameters) {
        return repository.findById(parameters.id).orElseThrow();
    }
}
