package pl.uniwersytetkaliski.studenteventsplatform.controller;

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
    public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String authorizationHeader) {
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

            return ResponseEntity.ok(auth);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
