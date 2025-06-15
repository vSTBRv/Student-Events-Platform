package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.uniwersytetkaliski.studenteventsplatform.dto.reportDTO.EventCountReportDTO;
import pl.uniwersytetkaliski.studenteventsplatform.service.ReportService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/event-count")
    @PreAuthorize("hasRole('ADMIN')")
    public EventCountReportDTO getEventCount(
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    ) {
        long count = reportService.countEventsBetweenAndDeletedFalseAndAcceptedTrue(fromDate, toDate);
        return new EventCountReportDTO(count);
    }
}
