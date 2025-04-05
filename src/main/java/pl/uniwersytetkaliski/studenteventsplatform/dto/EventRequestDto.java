package pl.uniwersytetkaliski.studenteventsplatform.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;

import java.time.LocalDateTime;

public class EventRequestDto {

    @NotNull
    private String name;

    @NotNull
    private long location_id;

    @NotNull
    private EventStatus status;

    @NotNull
    private int maxCapacity;

    @NotNull
    private LocalDateTime startTime;

    @Size(max = 1000)
    private LocalDateTime endTime;

}
