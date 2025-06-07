package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.repository.EventRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ReportService implements ReportServiceInterface {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public long countEventsBetweenAndDeletedFalseAndAcceptedTrue(LocalDateTime fromDate, LocalDateTime toDate) {
        return eventRepository.countByStartDateBetweenAndDeletedFalseAndAcceptedTrue(fromDate, toDate);
    }
}
