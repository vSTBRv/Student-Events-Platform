package pl.uniwersytetkaliski.studenteventsplatform.dto.reportDto;

public class ParticipantCountReportDTO {
    private long participantCount;

    public ParticipantCountReportDTO(long participantCount) {
        this.participantCount = participantCount;
    }

    public long getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(long participantCount) {
        this.participantCount = participantCount;
    }
}
