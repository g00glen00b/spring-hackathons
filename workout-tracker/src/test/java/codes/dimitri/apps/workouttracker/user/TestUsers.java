package codes.dimitri.apps.workouttracker.user;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class TestUsers {
    private static final UUID USER_1_ID = UUID.fromString("90bcce34-b4b5-418e-8c81-f095e638073c");
    private static final UUID USER_2_ID = UUID.fromString("7db47589-2a1e-47db-87a1-56f02427ec16");
    private static final UUID USER_3_ID = UUID.fromString("7edae0d7-8cc8-42e3-a826-bcb455e57c89");
    private final UserRepository repository;

    public User user1() {
        return repository.findById(USER_1_ID).orElseThrow();
    }

    public User user2() {
        return repository.findById(USER_2_ID).orElseThrow();
    }

    public User user3() {
        return repository.findById(USER_3_ID).orElseThrow();
    }
}
