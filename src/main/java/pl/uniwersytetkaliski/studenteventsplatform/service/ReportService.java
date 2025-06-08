package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.dto.reportDTO.ParticipantPerEventDTO;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;
import pl.uniwersytetkaliski.studenteventsplatform.repository.UserEventRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService implements ReportServiceInterface {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserEventRepository userEventRepository;

    @Override
    public long countEventsBetweenAndDeletedFalseAndAcceptedTrue(LocalDateTime fromDate, LocalDateTime toDate) {
        return eventRepository.countByStartDateBetweenAndDeletedFalseAndAcceptedTrue(fromDate, toDate);
    }

    @Override
    public long countParticipantsBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return userEventRepository.countParticipantBetweenDates(fromDate, toDate);
    }

    @Override
    public List<ParticipantPerEventDTO> getParticipantCountPerEvent(LocalDateTime from, LocalDateTime to) {
        return userEventRepository.countParticipantsGroupedByEvent(from, to);
    }
}
