package codes.dimitri.apps.workouttracker.workout;

import codes.dimitri.apps.workouttracker.TestcontainersConfiguration;
import codes.dimitri.apps.workouttracker.security.JwtUtils;
import codes.dimitri.apps.workouttracker.user.TestUsers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
    "jwt.secret=my-secret",
    "spring.jpa.show-sql=true"
})
@Import({TestcontainersConfiguration.class, TestUsers.class})
@AutoConfigureMockMvc
@Sql(
    scripts = {"classpath:test-data/users.sql", "classpath:test-data/workouts.sql", "classpath:test-data/schedules.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Sql(
    scripts = {"classpath:test-data/cleanup-schedules.sql", "classpath:test-data/cleanup-workouts.sql", "classpath:test-data/cleanup-users.sql"},
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
class WorkoutControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TestUsers testUsers;
    @Autowired
    private WorkoutRepository repository;

    @Test
    void createWorkout() throws Exception {
        var content = """
        {
            "name": "Daily workout",
            "exercises": [{
                "exerciseId": "1b8ccc44-312f-4fdd-8a87-4b8c0a3d8b1c",
                "reps": 5,
                "sets": 3,
                "weightInKg": 100
            }, {
                "exerciseId": "9d73b7cc-d76c-41ac-85f8-5946b7325b07",
                "reps": 4,
                "sets": 15
            }]
        }
        """;
        var user = testUsers.user1();
        mockMvc
            .perform(post("/workout")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Daily workout"))
            .andExpect(jsonPath("$.exercises[0].exercise.name").value("Deadlift"))
            .andExpect(jsonPath("$.exercises[0].reps").value(5))
            .andExpect(jsonPath("$.exercises[0].sets").value(3))
            .andExpect(jsonPath("$.exercises[0].weightInKg").value(100))
            .andExpect(jsonPath("$.exercises[1].exercise.name").value("Squat"))
            .andExpect(jsonPath("$.exercises[1].reps").value(4))
            .andExpect(jsonPath("$.exercises[1].sets").value(15));
        List<Workout> workouts = repository.findAll(WorkoutSpecifications.userId(user.getId()));
        assertThat(workouts).hasSize(2);
        assertThat(workouts)
            .extracting(Workout::getName)
            .containsOnly("Daily workout", "Advanced workout");
    }

    @Test
    void deleteWorkout() throws Exception {
        var user = testUsers.user1();
        var workoutId = UUID.fromString("7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3");
        mockMvc
            .perform(delete("/workout/{id}", workoutId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isNoContent());
        assertThat(repository.existsById(workoutId)).isFalse();
    }

    @Test
    void deleteWorkout_doesNothingIfWrongUser() throws Exception {
        var user = testUsers.user2();
        var workoutId = UUID.fromString("7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3");
        mockMvc
            .perform(delete("/workout/{id}", workoutId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isBadRequest());
        assertThat(repository.existsById(workoutId)).isTrue();
    }

    @Test
    void updateWorkout() throws Exception {
        var content = """
        {
            "name": "Advanced workout (new)",
            "exercises": [{
                "id": "7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3",
                "exerciseId": "1b8ccc44-312f-4fdd-8a87-4b8c0a3d8b1c",
                "reps": 7,
                "sets": 5,
                "weightInKg": 160
            }, {
                "exerciseId": "9d73b7cc-d76c-41ac-85f8-5946b7325b07",
                "reps": 4,
                "sets": 15
            }]
        }
        """;
        var user = testUsers.user1();
        var workoutId = UUID.fromString("7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3");
        mockMvc
            .perform(put("/workout/{id}", workoutId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(workoutId.toString()))
            .andExpect(jsonPath("$.name").value("Advanced workout (new)"))
            .andExpect(jsonPath("$.exercises[0].exercise.name").value("Deadlift"))
            .andExpect(jsonPath("$.exercises[0].reps").value(7))
            .andExpect(jsonPath("$.exercises[0].sets").value(5))
            .andExpect(jsonPath("$.exercises[0].weightInKg").value(160))
            .andExpect(jsonPath("$.exercises[1].exercise.name").value("Squat"))
            .andExpect(jsonPath("$.exercises[1].reps").value(4))
            .andExpect(jsonPath("$.exercises[1].sets").value(15))
            .andExpect(jsonPath("$.exercises[2]").doesNotExist());
        List<Workout> workouts = repository.findAll(WorkoutSpecifications.userId(user.getId()));
        assertThat(workouts).hasSize(1);
        assertThat(workouts)
            .extracting(Workout::getName)
            .containsOnly("Advanced workout (new)");
    }

    @Test
    void updateWorkout_doesNothingIfWrongUserOrId() throws Exception {
        var content = """
        {
            "name": "Advanced workout (new)",
            "exercises": [{
                "id": "7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3",
                "exerciseId": "1b8ccc44-312f-4fdd-8a87-4b8c0a3d8b1c",
                "reps": 7,
                "sets": 5,
                "weightInKg": 160
            }, {
                "exerciseId": "9d73b7cc-d76c-41ac-85f8-5946b7325b07",
                "reps": 4,
                "sets": 15
            }]
        }
        """;
        var user = testUsers.user2();
        var workoutId = UUID.fromString("7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3");
        mockMvc
            .perform(put("/workout/{id}", workoutId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isBadRequest());
        List<Workout> workouts = repository.findAll();
        assertThat(workouts).hasSize(1);
        assertThat(workouts)
            .extracting(Workout::getName)
            .containsOnly("Advanced workout");
    }

    @Test
    void listWorkouts() throws Exception {
        var user = testUsers.user1();
        mockMvc
            .perform(get("/workout")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .queryParam("sort", "name,asc")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value("Advanced workout"));
    }

    @ParameterizedTest
    @CsvSource({
        "2024-05-31,Europe/Brussels,0",
        "2024-06-01,Europe/Brussels,1",
        "2024-06-01,US/Hawaii,0",
        "2024-06-02,Europe/Brussels,0",
        "2024-06-03,Europe/Brussels,1",
        "2024-06-04,Europe/Brussels,0",
        "2024-06-05,Europe/Brussels,1",
        "2024-06-06,Europe/Brussels,0"
    })
    void listWorkouts_onSpecificDate(String date, String zoneId, int expectedResults) throws Exception {
        var user = testUsers.user1();
        mockMvc
            .perform(get("/workout")
                .queryParam("date", date)
                .queryParam("zoneId", zoneId)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .queryParam("sort", "name,asc")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value(expectedResults));
    }

    @Test
    void listWorkouts_doesNothingIfNoWorkouts() throws Exception {
        var user = testUsers.user2();
        mockMvc
            .perform(get("/workout")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .queryParam("sort", "name,asc")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0]").doesNotExist());
    }

    @Test
    void getWorkoutReport() throws Exception {
        var user = testUsers.user1();
        var workoutId = UUID.fromString("7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3");
        mockMvc
            .perform(get("/workout/{id}/report", workoutId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalTimesCompleted").value(2))
            .andExpect(jsonPath("$.averageDuration").value("PT1H7M30S"))
            .andExpect(jsonPath("$.shortestDuration").value("PT55M"))
            .andExpect(jsonPath("$.longestDuration").value("PT1H20M"))
            .andExpect(jsonPath("$.totalTimeSpent").value("PT2H15M"));
    }
}