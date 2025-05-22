package pl.uniwersytetkaliski.studenteventsplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequestDTO {
    @NotBlank
    public String content;
}
