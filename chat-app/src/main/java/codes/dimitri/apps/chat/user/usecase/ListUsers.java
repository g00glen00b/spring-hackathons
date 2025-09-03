package codes.dimitri.apps.chat.user.usecase;

import codes.dimitri.apps.chat.shared.UseCase;
import codes.dimitri.apps.chat.user.User;
import codes.dimitri.apps.chat.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

@UseCase(readOnly = true)
@RequiredArgsConstructor
public class ListUsers {
    private final UserRepository repository;

    public List<User> execute() {
        return repository.findAll(Sort.by("username"));
    }
}
