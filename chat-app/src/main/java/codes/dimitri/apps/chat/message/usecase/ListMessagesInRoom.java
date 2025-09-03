package codes.dimitri.apps.chat.message.usecase;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.MessageRepository;
import codes.dimitri.apps.chat.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.UUID;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListMessagesInRoom {
    private final MessageRepository repository;

    public record Parameters(UUID roomId, Instant before) {
        public Parameters {
            Assert.notNull(roomId, "roomId must not be null");
            Assert.notNull(before, "before must not be null");
            Assert.state(!before.isAfter(Instant.now()), "before must be before now");
        }
    }

    public Page<Message> execute(Parameters parameters) {
        var pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "createdAt");
        Page<Message> results = repository.findAllByRoomIdAndCreatedAtBefore(parameters.roomId, parameters.before, pageable);
        return new PageImpl<>(
            results.getContent(),
            results.getPageable(),
            results.getTotalElements()
        );
    }
}
