package pl.uniwersytetkaliski.studenteventsplatform.dto.eventDTO;

import pl.uniwersytetkaliski.studenteventsplatform.dto.UserDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.locationDTO.LocationResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;

import java.time.LocalDateTime;

public class EventResponseDTO {
    private Long id;
    private String name;
    private LocationResponseDTO locationDTO;
    private EventStatus status;
    private int maxCapacity;
    private int currentCapacity;
    private LocalDateTime creationDate;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private CategoryResponseDTO categoryDTO;
    private boolean deleted;
    private LocalDateTime deletedAt;
    private UserDTO createdBy;
    private boolean accepted;

    private boolean participating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationResponseDTO getLocationDTO() {
        return locationDTO;
    }

    public void setLocationDTO(LocationResponseDTO locationDTO) {
        this.locationDTO = locationDTO;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public CategoryResponseDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryResponseDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isParticipating() {
        return participating;
    }
    public void setParticipating(boolean participating) {
        this.participating = participating;
    }
}
