package pl.uniwersytetkaliski.studenteventsplatform.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.RegisterDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserRole;
import pl.uniwersytetkaliski.studenteventsplatform.service.NotificationService;
import pl.uniwersytetkaliski.studenteventsplatform.service.UserService;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public AuthController(AuthenticationManager authenticationManager, NotificationService notificationService, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.notificationService = notificationService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {

        if (userService.existsByEmail(registerDTO.email)) {
            return ResponseEntity.badRequest().body("Email już istnieje.");
        }
        if (Objects.equals(registerDTO.userRole, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized.");
        }

        User user = new User();

        user.setEmail(registerDTO.email);
        user.setUsername(registerDTO.username != null ? registerDTO.username : registerDTO.email);
        user.setFullName(registerDTO.fullName);
        user.setUserRole(UserRole.valueOf(registerDTO.userRole.toUpperCase()));
        user.setPassword(passwordEncoder.encode(registerDTO.password));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        userService.createUser(user);

        try {
            notificationService.sendRegistrationEmail(user.getEmail(), user.getFullName());
        } catch (MailSendException ignored){}

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request
    ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session  = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            request.getSession(true);

            System.out.println("ZALOGOWANO: " + authentication.getName());
            System.out.println("SESSION ID: " + request.getSession().getId());

            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Obsługuje żądanie POST pod adresem /logout (pełna ścieżka /api/logout
     * @param request żądanie przychodzące od klienta
     * @param response odpowiedź którą backend wysyła z powrotem
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        // Unieważnia bieżącą sesję użytkownika po stronie serwera
        request.getSession().invalidate();

        // Tworzy ciasteczko JSESSIONID z wartością null i czasem życia 0 – przeglądarka usunie je
        Cookie cookie = new Cookie("JSESSIONID", null);

        // Ciasteczko dostępne na całej ścieżce aplikacji
        cookie.setPath("/");

        // Zabezpiecza ciasteczko przed dostępem z JavaScript (ochrona przed XSS)
        cookie.setHttpOnly(true);

        // Ustawia czas życia ciasteczka na 0 – przeglądarka je natychmiast usuwa
        cookie.setMaxAge(0);

        // Dodaje ciasteczko do odpowiedzi – przeglądarka je odbierze i usunie
        response.addCookie(cookie);

        // Zwraca odpowiedź 200 OK z informacją o wylogowaniu
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email)
                .map(user->ResponseEntity.ok(new UserDTO(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
