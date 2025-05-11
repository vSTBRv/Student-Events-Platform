package pl.uniwersytetkaliski.studenteventsplatform.dto;

public class UserDTO {
    String fullName;

    public UserDTO(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
