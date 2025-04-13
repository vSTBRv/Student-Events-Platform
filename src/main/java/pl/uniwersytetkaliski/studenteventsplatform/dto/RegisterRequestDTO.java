package pl.uniwersytetkaliski.studenteventsplatform.dto;

public class RegisterRequestDTO {
        public String email;
        public String password;
        public String fullName;
        public String userRole; // "STUDENT" lub "ORGANIZATION"
        public String username; // opcjonalnie (może być email)
}
