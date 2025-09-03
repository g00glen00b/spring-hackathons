package codes.dimitri.apps.chat.message.usecase;

import codes.dimitri.apps.chat.message.Message;
import codes.dimitri.apps.chat.message.MessageRepository;
import codes.dimitri.apps.chat.shared.UseCase;
import codes.dimitri.apps.chat.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ReadMessage {
    private final MessageRepository repository;
    private final UserRepository userRepository;

    public record Parameters(UUID userId, UUID messageId) {}

    public Message execute(Parameters parameters) {
        Message message = repository.findById(parameters.messageId).orElseThrow();
        var user =  userRepository.findById(parameters.userId).orElseThrow();
        message.addReadUser(user);
        return message;
    }
}
