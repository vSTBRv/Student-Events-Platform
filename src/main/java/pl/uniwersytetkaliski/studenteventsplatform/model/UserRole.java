package pl.uniwersytetkaliski.studenteventsplatform.model;

public enum UserRole {
    ADMIN("Administrator"),
    ORGANIZATION("Organizacja"),
    STUDENT("Student");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
