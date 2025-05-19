package pl.uniwersytetkaliski.studenteventsplatform.dto;

import pl.uniwersytetkaliski.studenteventsplatform.model.User;

public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String userRole;

    public UserDTO(User user) {
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.userRole = user.getUserRole().toString();
        this.id = user.getId();
    }

    UserDTO() {}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
