package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.uniwersytetkaliski.studenteventsplatform.dto.reportdto.EventCountReportDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.reportdto.ParticipantCountReportDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.reportdto.ParticipantPerEventDTO;
import pl.uniwersytetkaliski.studenteventsplatform.service.ReportService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/event-count")
    @PreAuthorize("hasRole('ADMIN')")
    public EventCountReportDTO getEventCount(
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    ) {
        long count = reportService.countEventsBetweenAndDeletedFalseAndAcceptedTrue(fromDate, toDate);
        return new EventCountReportDTO(count);
    }


    @GetMapping("/participant-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ParticipantCountReportDTO getParticipantCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    ) {
        long count = reportService.countParticipantsBetween(fromDate, toDate);
        return new ParticipantCountReportDTO(count);
    }

    @GetMapping("/participant-count-per-event")
    public List<ParticipantPerEventDTO> getParticipantCountPerEvent(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    ) {
        return reportService.getParticipantCountPerEvent(fromDate, toDate);
    }

}
