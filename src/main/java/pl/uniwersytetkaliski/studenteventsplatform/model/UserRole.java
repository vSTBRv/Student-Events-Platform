package pl.uniwersytetkaliski.studenteventsplatform.model;

public enum UserRole {
    ADMIN("ADMIN"),
    ORGANIZATION("ORGANIZATION"),
    STUDENT("STUDENT");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
