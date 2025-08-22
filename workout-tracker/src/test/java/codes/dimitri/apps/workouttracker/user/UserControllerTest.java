package codes.dimitri.apps.workouttracker.user;

import codes.dimitri.apps.workouttracker.TestcontainersConfiguration;
import codes.dimitri.apps.workouttracker.security.JwtUtils;
import codes.dimitri.apps.workouttracker.user.web.TokenDTO;
import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import({TestcontainersConfiguration.class, TestUsers.class})
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-data/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data/cleanup-users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTVerifier jwtVerifier;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TestUsers testUsers;

    @Test
    void getToken_returnsJwt() throws Exception {
        var response = mockMvc
            .perform(post("/user/token")
                .with(httpBasic("user1", "password1")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isString())
            .andReturn()
            .getResponse()
            .getContentAsString();
        var token = objectMapper.readValue(response, TokenDTO.class);
        var decodedToken = jwtVerifier.verify(token.token());
        assertThat(decodedToken.getIssuer()).isEqualTo("workout-tracker");
        assertThat(decodedToken.getSubject()).isEqualTo("user1");
        assertThat(decodedToken.getExpiresAt()).isCloseTo(Instant.now().plus(1, ChronoUnit.HOURS), 5_000L);
    }

    @Test
    void getToken_failsIfWrongPassword() throws Exception {
        mockMvc
            .perform(post("/user/token")
                .with(httpBasic("user1", "wrongpassword")))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.token").doesNotExist());
    }

    @Test
    void getUserInfo_returnsInfo() throws Exception {
        User user1 = testUsers.user1();
        mockMvc
            .perform(get("/user/info")
                .header("Authorization", "Bearer " + jwtUtils.token(user1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sub").value("user1"))
            .andExpect(jsonPath("$.iss").value("workout-tracker"));
    }
}