//package pl.uniwersytetkaliski.studenteventsplatform.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import pl.uniwersytetkaliski.studenteventsplatform.exception.UserNotFoundException;
//import pl.uniwersytetkaliski.studenteventsplatform.model.User;
//import pl.uniwersytetkaliski.studenteventsplatform.model.UserRole;
//import pl.uniwersytetkaliski.studenteventsplatform.repository.UserRepository;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User preparedUser;
//
//    @BeforeEach
//    void setUp() {
//        preparedUser = userService.createUser(
//                User.builder()
//                        .username("anka_chuliganka")
//                        .fullName("Anna Kowalska")
//                        .email("ania@kowalska.com")
//                        .password("admin123")
//                        .userRole(UserRole.STUDENT)
//                        .build()
//                );
//    }
//
//    @Test
//    void shouldGetUserById() {
//        Optional<User> user = userService.getUserById(1L);
//
//        assertTrue(user.isPresent()); // sprawdza, czy dany user istnieje
//        assertEquals("student", user.get().getUsername());
//        assertEquals("testStudent", user.get().getFullName());
//        assertEquals(UserRole.STUDENT, user.get().getUserRole());
//    }
//
//    @Test
//    void shouldCreateUser() {
//        // given
//        User user = User.builder()
//                .username("test123")
//                .fullName("Test Czester")
//                .email("test@example.com")
//                .password("haslo")
//                .userRole(UserRole.STUDENT)
//                .enabled(true)
//                .build();
//
//        // when
//        User saved = userService.createUser(user);
//
//        // then
//        assertNotNull(saved);
//        assertEquals("test123", saved.getUsername());
//    }
//
//    @Test
//    void shouldUpdateUser() {
//        User updatedUser = new User();
//        String login = "anka_ponczoszanka";
//        String fullName = "Anka Ponczoszanka";
//        String email = "ania@ponczoszanka.com";
//        String password = "123haslo123";
//        UserRole userRole = UserRole.ADMIN;
//        boolean enabled = false;
//
//        updatedUser.setUsername(login);
//        updatedUser.setFullName(fullName);
//        updatedUser.setEmail(email);
//        updatedUser.setPassword(password);
//        updatedUser.setUserRole(userRole);
//        updatedUser.setEnabled(enabled);
//
//        User result = userService.updateUser(preparedUser.getId(), updatedUser);
//
//        assertNotNull(result);
//        assertEquals(login, result.getUsername());
//        assertEquals(fullName, result.getFullName());
//        assertEquals(email, result.getEmail());
//        assertEquals(password, result.getPassword());
//        assertEquals(userRole, result.getUserRole());
//        assertEquals(enabled, result.isEnabled());
//    }
//
//    @Test
//    void shouldDeleteUser() {
//        userService.deleteUser(preparedUser.getId());
//        Optional<User> user = userService.getUserById(preparedUser.getId());
//        assertTrue(user.isEmpty());
//    }
//
//    @Test
//    void shouldThrowWnenDeletingNonExistingUser() {
//        Long nonExistingUserId = 999L; // nie istnieje użytkownik o takim ID
//        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(nonExistingUserId));
//    }
//}
