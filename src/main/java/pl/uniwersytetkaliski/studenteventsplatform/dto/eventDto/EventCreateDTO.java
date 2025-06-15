package pl.uniwersytetkaliski.studenteventsplatform.dto.eventDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationDto.LocationCreateDTO;

import java.time.LocalDateTime;

public class EventCreateDTO {
    @NotBlank
    private String name;

    private LocationCreateDTO location;
    @Positive
    private Long categoryId;
    @NotNull(message="Start date musi być ustawione")
    private LocalDateTime startDateTime;
    @NotNull(message="End date musi być ustawione")
    private LocalDateTime endDateTime;
    private String description;
    @Positive
    private int maxCapacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationCreateDTO getLocation() {
        return location;
    }

    public void setLocation(LocationCreateDTO location) {
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
}
