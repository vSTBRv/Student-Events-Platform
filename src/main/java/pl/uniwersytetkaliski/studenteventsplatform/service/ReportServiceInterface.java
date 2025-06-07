package pl.uniwersytetkaliski.studenteventsplatform.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportServiceInterface {
    long countEventsBetweenAndDeletedFalseAndAcceptedTrue(LocalDateTime fromDate, LocalDateTime toDate);
}
