package pl.uniwersytetkaliski.studenteventsplatform.service;

import pl.uniwersytetkaliski.studenteventsplatform.dto.reportdto.ParticipantPerEventDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportServiceInterface {
    long countEventsBetweenAndDeletedFalseAndAcceptedTrue(LocalDateTime fromDate, LocalDateTime toDate);

    long countParticipantsBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<ParticipantPerEventDTO> getParticipantCountPerEvent(LocalDateTime from, LocalDateTime to);


}
