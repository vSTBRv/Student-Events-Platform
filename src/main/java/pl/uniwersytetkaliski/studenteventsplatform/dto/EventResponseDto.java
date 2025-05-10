package pl.uniwersytetkaliski.studenteventsplatform.dto;

import pl.uniwersytetkaliski.studenteventsplatform.model.EventStatus;

import java.time.LocalDateTime;

public class EventResponseDto {
    private Long id;
    private String name;
//    private String description;
    private String locationCity;
    private String locationStreet;
    private String locationHouseNumber;
    private String locationPostalCode;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String comments;
    private EventStatus status;
    private int capacity;
    private String statusLabel;
    private String category;
    private String createdBy;
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

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationStreet() {
        return locationStreet;
    }

    public void setLocationStreet(String locationStreet) {
        this.locationStreet = locationStreet;
    }

    public String getLocationHouseNumber() {
        return locationHouseNumber;
    }

    public void setLocationHouseNumber(String locationHouseNumber) {
        this.locationHouseNumber = locationHouseNumber;
    }

    public String getLocationPostalCode() {
        return locationPostalCode;
    }

    public void setLocationPostalCode(String locationPostalCode) {
        this.locationPostalCode = locationPostalCode;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getStatusLabel() {
        return status.getStatus(); // zwróci "Zaplanowane", "W trakcie" itd.
    }
    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isParticipating() {
        return participating;
    }

    public void setParticipating(boolean participating) {
        this.participating = participating;
    }

}
