package pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CategoryCreateDTO {
    @NotBlank
    @Pattern(regexp = "^\\D*$")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
