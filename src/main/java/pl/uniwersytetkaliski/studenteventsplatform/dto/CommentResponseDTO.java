package pl.uniwersytetkaliski.studenteventsplatform.dto;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    public Long id;
    public String authorName;
    public String content;
    public LocalDateTime createdAt;
}
