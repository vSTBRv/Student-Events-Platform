package pl.uniwersytetkaliski.studenteventsplatform.dto.reportDTO;

import java.time.LocalDateTime;

public class ParticipantPerEventDTO {
    private Long eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private long participantCount;

    public ParticipantPerEventDTO(Long eventId, String eventName, LocalDateTime eventDate, long participantCount) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.participantCount = participantCount;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public long getParticipantCount() {
        return participantCount;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public void setParticipantCount(long participantCount) {
        this.participantCount = participantCount;
    }
}
