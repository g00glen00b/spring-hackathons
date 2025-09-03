package codes.dimitri.apps.chat.message.usecase;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.MessageRepository;
import codes.dimitri.apps.chat.shared.UseCase;
import codes.dimitri.apps.chat.user.User;
import codes.dimitri.apps.chat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class SendMessageToRoom {
    private final MessageRepository repository;
    private final UserRepository userRepository;

    public record Parameters(UUID roomId, UUID userId, String message) {
        public Parameters {
            Assert.notNull(roomId, "roomId must not be null");
            Assert.notNull(userId, "userId must not be null");
            Assert.notNull(message, "message must not be null");
            Assert.state(!message.isEmpty(), "message must not be empty");
            Assert.state(message.length() <= 256, "message length must be less than 256 characters");
        }
    }

    public Message execute(Parameters parameters) {
        User user = userRepository.findById(parameters.userId).orElseThrow();
        return repository.save(new Message(user, parameters.roomId, parameters.message));
    }
}
