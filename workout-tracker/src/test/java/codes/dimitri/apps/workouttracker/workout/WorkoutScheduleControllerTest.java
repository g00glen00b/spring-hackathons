package codes.dimitri.apps.workouttracker.workout;

import codes.dimitri.apps.workouttracker.TestcontainersConfiguration;
import codes.dimitri.apps.workouttracker.security.JwtUtils;
import codes.dimitri.apps.workouttracker.user.TestUsers;
import codes.dimitri.apps.workouttracker.workout.web.WorkoutScheduleDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
    "jwt.secret=my-secret"
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
class WorkoutScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TestUsers testUsers;
    @Autowired
    private WorkoutScheduleRepository scheduleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addSchedule() throws Exception {
        var content = """
        {
            "plannedStart": "2025-07-01T06:00:00Z"
        }
        """;
        var user = testUsers.user1();
        MvcResult result = mockMvc
            .perform(post("/workout/{id}/schedule", "7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.plannedStart").value("2025-07-01T06:00:00Z"))
            .andReturn();
        WorkoutScheduleDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), WorkoutScheduleDTO.class);
        WorkoutSchedule schedule = scheduleRepository.findById(response.id()).orElseThrow();
        assertThat(schedule)
            .usingRecursiveComparison()
            .isEqualTo(new WorkoutSchedule(
                response.id(),
                schedule.getWorkout(),
                Instant.parse("2025-07-01T06:00:00Z"),
                null,
                null
            ));
    }

    @Test
    void addSchedule_failsIfWorkoutDoesNotBelongToUser() throws Exception {
        var content = """
        {
            "plannedStart": "2025-07-01T06:00:00Z"
        }
        """;
        var user = testUsers.user2();
        mockMvc
            .perform(post("/workout/{id}/schedule", "7a591c1c-a2e0-4b87-9fee-e24e47a1b6d3")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateSchedule() throws Exception {
        var id = UUID.fromString("b1a2c3d4-e5f6-7890-abcd-ef1234567890");
        var user = testUsers.user1();
        var content = """
        {
            "plannedStart": "2024-06-01T06:00:00Z",
            "actualStart": "2024-06-01T06:05:00Z",
            "actualEnd": "2024-06-01T07:00:00Z"
        }
        """;
        mockMvc
            .perform(put("/workout/schedule/{id}", id)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.plannedStart").value("2024-06-01T06:00:00Z"))
            .andExpect(jsonPath("$.actualStart").value("2024-06-01T06:05:00Z"))
            .andExpect(jsonPath("$.actualEnd").value("2024-06-01T07:00:00Z"));
        WorkoutSchedule schedule = scheduleRepository.findById(id).orElseThrow();
        assertThat(schedule)
            .usingRecursiveComparison()
            .isEqualTo(new WorkoutSchedule(
                id,
                schedule.getWorkout(),
                Instant.parse("2024-06-01T06:00:00Z"),
                Instant.parse("2024-06-01T06:05:00Z"),
                Instant.parse("2024-06-01T07:00:00Z")
            ));
    }

    @Test
    void updateSchedule_failsIfNotBelongingToUser() throws Exception {
        var id = UUID.fromString("b1a2c3d4-e5f6-7890-abcd-ef1234567890");
        var user = testUsers.user2();
        var content = """
        {
            "plannedStart": "2024-06-01T06:00:00Z",
            "actualStart": "2024-06-01T06:05:00Z",
            "actualEnd": "2024-06-01T07:00:00Z"
        }
        """;
        mockMvc
            .perform(put("/workout/schedule/{id}", id)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isBadRequest());
        WorkoutSchedule schedule = scheduleRepository.findById(id).orElseThrow();
        assertThat(schedule)
            .usingRecursiveComparison()
            .isEqualTo(new WorkoutSchedule(
                id,
                schedule.getWorkout(),
                Instant.parse("2024-06-01T08:00:00Z"),
                null,
                null
            ));
    }

    @Test
    void deleteSchedule() throws Exception {
        var id = UUID.fromString("b1a2c3d4-e5f6-7890-abcd-ef1234567890");
        var user = testUsers.user1();
        mockMvc
            .perform(delete("/workout/schedule/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isNoContent());
        assertThat(scheduleRepository.existsById(id)).isFalse();
    }

    @Test
    void deleteSchedule_failsIfScheduleDoesNotBelongToUser() throws Exception {
        var id = UUID.fromString("b1a2c3d4-e5f6-7890-abcd-ef1234567890");
        var user = testUsers.user2();
        mockMvc
            .perform(delete("/workout/schedule/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isBadRequest());
        assertThat(scheduleRepository.existsById(id)).isTrue();
    }
}