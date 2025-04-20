package pl.uniwersytetkaliski.studenteventsplatform.dto;

public class RegisterDTO {
        public String email;
        public String password;
        public String fullName;
        public String userRole; // "STUDENT" lub "ORGANIZATION"
        public String username; // opcjonalnie (może być email)
}
