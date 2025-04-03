package pl.uniwersytetkaliski.studenteventsplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;    // login

    @Column(nullable = false)
    private String password;    // wiadomo

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    UserRole userRole;                // opis roli użytkownika

    private boolean enabled; // czy dany użytkownik jest aktywny

    private LocalDateTime createdAt;    // data utworzenia wpisu
}
