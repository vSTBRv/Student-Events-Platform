package pl.uniwersytetkaliski.studenteventsplatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EventResponseDto {
    private Long id;
    private String name;
    private String description;
    private String locationCity;
    private String locationStreet;
    private String locationHouseNumber;
    private String locationPostalCode;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String comments;
}
