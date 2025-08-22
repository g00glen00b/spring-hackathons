package codes.dimitri.apps.workouttracker.exercise;

import codes.dimitri.apps.workouttracker.TestcontainersConfiguration;
import codes.dimitri.apps.workouttracker.security.JwtUtils;
import codes.dimitri.apps.workouttracker.user.TestUsers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
    "jwt.secret=my-secret"
})
@Import({TestcontainersConfiguration.class, TestUsers.class})
@Sql(scripts = "classpath:test-data/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data/cleanup-users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureMockMvc
class ExerciseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TestUsers testUsers;

    @Test
    void getAll() throws Exception {
        var user = testUsers.user1();
        mockMvc
            .perform(get("/exercise")
                .queryParam("page", "0")
                .queryParam("size", "3")
                .queryParam("sort", "name,asc")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value(12))
            .andExpect(jsonPath("$.content[0].name").value("Bench Press"))
            .andExpect(jsonPath("$.content[1].name").value("Bicep Curl"))
            .andExpect(jsonPath("$.content[2].name").value("Cat-Cow"));
    }
}