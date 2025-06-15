package pl.uniwersytetkaliski.studenteventsplatform.dto.eventdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationdto.LocationUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;

import java.time.LocalDateTime;

public class EventUpdateDTO {
    @NotBlank
    private String name;

    private LocationUpdateDTO location;
    @Positive
    private Long categoryId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private EventStatus status;
    @Positive
    private int maxCapacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationUpdateDTO getLocation() {
        return location;
    }

    public void setLocation(LocationUpdateDTO location) {
        this.location = location;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public EventStatus getStatus() {
        return status;
    }
    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
