package pl.uniwersytetkaliski.studenteventsplatform.dto.locationDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LocationCreateDTO {
    @NotBlank
    @Pattern(regexp = "^\\D*$")
    private String city;

    private String street;

    @NotBlank
    private String houseNumber;

    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{3}")
    private String postalCode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
