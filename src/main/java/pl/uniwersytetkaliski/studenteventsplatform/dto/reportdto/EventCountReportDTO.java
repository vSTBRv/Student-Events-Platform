package pl.uniwersytetkaliski.studenteventsplatform.dto.reportdto;

public class EventCountReportDTO {
    private long eventCount;

    public EventCountReportDTO(long eventCount) {
        this.eventCount = eventCount;
    }

    public long getEventCount() {
        return eventCount;
    }

    public void setEventCount(long eventCount) {
        this.eventCount = eventCount;
    }
}
