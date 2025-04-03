package pl.uniwersytetkaliski.studenteventsplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.uniwersytetkaliski.studenteventsplatform.config.SecurityConfig;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserRole;
import pl.uniwersytetkaliski.studenteventsplatform.service.UserService;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.awaitility.Awaitility.given;

@WebMvcTest(controllers = UserController.class) // tutaj pokazuje co testujemy
@Import(SecurityConfig.class)
@AutoConfigureMockMvc       // umożliwia automatyczne wstrzykiwanie MockMvc bez uruchamiania serwera
@ActiveProfiles("test")     // odpalamy profil test
public class UserControllerTest {

    // poniżej pozwalamy wykonywać zapytania HTTP bez serwera
    @Autowired
    private MockMvc mockMvc;

    // tworzymy fałszywą instancję która zwraca to co mu każe
    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @WithMockUser   // ponieważ używamy SpringSecurity - dzięki tej adnotacji możemy obejść się bez fizycznego logowania
    @Test
    void shouldReturnUserById() throws Exception {
        // tworzenie przykładowego użytkownika
        User user = new User();
        user.setId(1L);
        user.setUsername("Janek");

        // gdy UserController w środku wywoła userService.getUserById(1L) to dostanie tego naszego usera
        given(userService.getUserById(1L)).willReturn(Optional.of(user));

        // wysyłanie zapytania GET na /api/users/1, sprawdzamy czy:
        // - odpowiedzią jest HTTP kod 200 (OK)
        // - czy w odpowiedzi otrzymujemy JSON z polem username o wartości "Janek"
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Janek"));
    }

//    @WithMockUser
    @Test
    void shouldCreateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("Janek");
        user.setEmail("janek@gmail.com");
        user.setPassword("password");
        user.setFullName("Jan Kowalski");
        user.setUserRole(UserRole.STUDENT);
        user.setEnabled(true);

        given(userService.createUser(user)).willReturn(user);

        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Janek"))
                .andExpect(jsonPath("$.email").value("janek@gmail.com"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.fullName").value("Jan Kowalski"));
//                .andExpect(jsonPath("$.userRole").value(UserRole.STUDENT.getDisplayName()));

    }
}
