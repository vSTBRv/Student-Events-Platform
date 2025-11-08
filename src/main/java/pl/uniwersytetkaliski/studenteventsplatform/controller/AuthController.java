package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.service.UserService;

/**
 * Controller for authentication operations with Keycloak
 *
 * Note: Login and registration are now handled by Keycloak.
 * This controller only provides endpoints to retrieve current user info.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get current authenticated user information from JWT token
     *
     * @param authentication Spring Security authentication object containing JWT
     * @return UserDTO with user information from Keycloak token
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();

        // Extract user information from JWT claims
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");
        String fullName = jwt.getClaimAsString("name");
        String role = jwt.getClaimAsString("role");

        // Create UserDTO from JWT claims
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setFullName(fullName != null ? fullName : username);
        userDTO.setUserRole(role != null ? role : "STUDENT");

        // Try to get ID from local database if user exists
        userService.getUserByUsername(username).ifPresent(user -> {
            userDTO.setId(user.getId());
        });

        return ResponseEntity.ok(userDTO);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}