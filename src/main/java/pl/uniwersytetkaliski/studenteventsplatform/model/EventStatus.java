package pl.uniwersytetkaliski.studenteventsplatform.model;

public enum EventStatus {
    PLANNED("Zaplanowane"),
    ONGOING("W trakcie"),
    COMPLETED("Zakończone"),
    CANCELLED("Odwołane");

    private String status;
    EventStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
