package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CommentRequestDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CommentResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.RatingRequestDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.RatingResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.service.CommentRatingService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}")
public class CommentRatingController {
    private final CommentRatingService commentRatingService;

    public CommentRatingController(CommentRatingService commentRatingService) {
        this.commentRatingService = commentRatingService;
    }

    @GetMapping("/comments")
    public List<CommentResponseDTO> getComments(@PathVariable Long eventId) {
        return commentRatingService.getCommentsForEvent(eventId);
    }

    @PostMapping("/comments")
    public ResponseEntity<Void> addComment(
            @PathVariable Long eventId,
            @RequestBody CommentRequestDTO commentRequestDTO,
            Principal principal
    ) {
        commentRatingService.addComment(eventId, commentRequestDTO, principal);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rating")
    public RatingResponseDTO getRating(@PathVariable Long eventId, Principal principal) {
        return commentRatingService.getRating(eventId, principal);
    }

    @PostMapping("/rating")
    public ResponseEntity<Void> addRating(
            @PathVariable Long eventId,
            @RequestBody RatingRequestDTO ratingRequestDTO,
            Principal principal
            ) {
        commentRatingService.addOrUpdateRating(eventId, ratingRequestDTO, principal);
        return ResponseEntity.ok().build();
    }
}
