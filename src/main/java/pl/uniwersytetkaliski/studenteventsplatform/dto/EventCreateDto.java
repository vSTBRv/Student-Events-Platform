package pl.uniwersytetkaliski.studenteventsplatform.dto;

import java.time.LocalDateTime;

public class EventCreateDto {
    private String name;
    private Long locationId;            // Location jest rekordem innej tabeli, stąd Long
    private String status;              // zostanie zmapowane do Enuma
    private int maxCapacity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String comments;
    private Long categoryId;
    private String createdBy;

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategory(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String eventName) {
        this.name = eventName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
