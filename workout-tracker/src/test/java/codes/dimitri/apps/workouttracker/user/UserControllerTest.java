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
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@SpringBootTest(properties = {
    "jwt.secret=my-secret"
})
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
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getToken_returnsJwt() throws Exception {
        var response = mockMvc
            .perform(get("/user/token")
                .with(httpBasic("user1", "password1")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isString())
            .andReturn()
            .getResponse()
            .getContentAsString();
        var token = objectMapper.readValue(response, TokenDTO.class);
        var decodedToken = jwtVerifier.verify(token.token());
        assertThat(decodedToken.getIssuer()).isEqualTo("workout-tracker");
        assertThat(decodedToken.getSubject()).isEqualTo("90bcce34-b4b5-418e-8c81-f095e638073c");
        assertThat(decodedToken.getClaim("username").asString()).isEqualTo("user1");
        assertThat(decodedToken.getExpiresAt()).isCloseTo(Instant.now().plus(1, ChronoUnit.HOURS), 5_000L);
    }

    @Test
    void getToken_failsIfWrongPassword() throws Exception {
        mockMvc
            .perform(get("/user/token")
                .with(httpBasic("user1", "wrongpassword")))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.token").doesNotExist());
    }

    @Test
    void getUserInfo_returnsInfo() throws Exception {
        User user1 = testUsers.user1();
        mockMvc
            .perform(get("/user/info")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.token(user1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sub").value("90bcce34-b4b5-418e-8c81-f095e638073c"))
            .andExpect(jsonPath("$.username").value("user1"))
            .andExpect(jsonPath("$.iss").value("workout-tracker"));
    }

    @Test
    void registerUser() throws Exception {
        var content = """
        {
            "username": "newuser",
            "password": "newpassword"
        }
        """;
        mockMvc
            .perform(post("/user")
                .contentType("application/json")
                .content(content))
            .andExpect(status().isCreated());
        User result = repository.findByUsername("newuser").orElseThrow();
        assertThat(result.getUsername()).isEqualTo("newuser");
        assertThat(passwordEncoder.matches("newpassword", result.getPassword())).isTrue();
    }

    @Test
    void registerUser_failsIfUsernameAlreadyExists() throws Exception {
        var content = """
        {
            "username": "user1",
            "password": "newpassword"
        }
        """;
        mockMvc
            .perform(post("/user")
                .contentType("application/json")
                .content(content))
            .andExpect(status().isBadRequest());
    }
}