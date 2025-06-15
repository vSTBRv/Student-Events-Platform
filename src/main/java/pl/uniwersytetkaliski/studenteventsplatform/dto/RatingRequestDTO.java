package pl.uniwersytetkaliski.studenteventsplatform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class RatingRequestDTO {
    @Min(1)
    @Max(5)
    public int value; // od 1 do 5
}
