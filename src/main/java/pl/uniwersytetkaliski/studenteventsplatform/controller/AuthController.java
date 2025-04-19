package pl.uniwersytetkaliski.studenteventsplatform.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.RegisterRequestDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.model.UserRole;
import pl.uniwersytetkaliski.studenteventsplatform.service.UserService;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO requestDTO) {
        if (userService.existsByEmail(requestDTO.email)) {
            return ResponseEntity.badRequest().body("Email już istnieje.");
        }
        if (Objects.equals(requestDTO.userRole, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized.");
        }

        User user = new User();

        user.setEmail(requestDTO.email);
        user.setUsername(requestDTO.username != null ? requestDTO.username : requestDTO.email);
        user.setFullName(requestDTO.fullName);
        user.setUserRole(UserRole.valueOf(requestDTO.userRole.toUpperCase()));
        user.setPassword(passwordEncoder.encode(requestDTO.password));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Login endpoint for HTTP Basic Authentication
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String authorizationHeader, HttpServletRequest request) {
        try {
            // Extract credentials from the Basic Auth header
            String base64Credentials = authorizationHeader.substring("Basic ".length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            // Authenticate using the extracted username and password
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(auth);

            // poniższa metoda tworzy sesję i uruchamia mechanizm JSESSIONID
            // bez tej metody nie działa logowanie i wylogowanie
            request.getSession(true);

            return ResponseEntity.ok(auth);
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
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

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
        return ResponseEntity.ok("Wylogowano");
    }
}
