package codes.dimitri.apps.workouttracker;

import org.springframework.boot.SpringApplication;

public class TestWorkoutTrackerApplication {
    public static void main(String[] args) {
        SpringApplication
            .from(TestWorkoutTrackerApplication::main)
            .with(TestcontainersConfiguration.class)
            .run(args);
    }
}
