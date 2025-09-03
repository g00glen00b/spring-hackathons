package codes.dimitri.apps.chat.user.usecase;

import codes.dimitri.apps.chat.shared.UseCase;
import codes.dimitri.apps.chat.user.User;
import codes.dimitri.apps.chat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateUserTypingState {
    private final UserRepository repository;

    public record Parameters(UUID userId, boolean typing) {
        public Parameters {
            Assert.notNull(userId,  "userId must not be null");
        }
    }

    public User execute(Parameters parameters) {
        User user = repository.findById(parameters.userId).orElseThrow();
        user.setTyping(parameters.typing);
        return user;
    }
}
