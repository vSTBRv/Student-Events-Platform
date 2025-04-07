package pl.uniwersytetkaliski.studenteventsplatform.dto;

//import lombok.Getter;
//import lombok.Setter;

import java.time.LocalDateTime;

//@Setter
//@Getter
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
