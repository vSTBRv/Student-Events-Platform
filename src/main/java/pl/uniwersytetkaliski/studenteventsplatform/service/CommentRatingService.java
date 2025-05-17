package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CommentRequestDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CommentResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.RatingRequestDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.RatingResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Comment;
import pl.uniwersytetkaliski.studenteventsplatform.model.Event;
import pl.uniwersytetkaliski.studenteventsplatform.model.Rating;
import pl.uniwersytetkaliski.studenteventsplatform.model.User;
import pl.uniwersytetkaliski.studenteventsplatform.repository.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentRatingService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EventRepository eventService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventRepository userEventRepository;
    @Autowired
    private EventRepository eventRepository;

    public List<CommentResponseDTO> getCommentsForEvent(Long eventId) {
        return commentRepository.findByEventId(eventId)
                .stream()
                .map(comment -> {
                    CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
                    commentResponseDTO.id = comment.getId();
                    commentResponseDTO.authorName = comment.getAuthor().getFullName();
                    commentResponseDTO.content = comment.getContent();
                    commentResponseDTO.createdAt = comment.getCreatedAt();
                    return commentResponseDTO;
                })
                .collect(Collectors.toList());
    }

    public void addComment(Long eventId, CommentRequestDTO commentRequestDTO, Principal principal) {
        User user = getUserFromPrincipal(principal);
        Event event = getEventFromPrincipal(eventId);

        if(!userEventRepository.existsByUserAndEvent(user, event)) {
            throw new RuntimeException("Tylko uczestnicy wydarzenia mogą komentować");
        }

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setContent(commentRequestDTO.content);
        comment.setEvent(event);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public void addOrUpdateRating(Long eventId, RatingRequestDTO dto, Principal principal) {
        User user = getUserFromPrincipal(principal);
        Event event = getEventFromPrincipal(eventId);
        if(!userEventRepository.existsByUserAndEvent(user, event)) {
            throw new RuntimeException("Tylko uczestnicy wydarzenia mogą oceniać");
        }

        Rating rating = ratingRepository.findByUserAndEvent(user, event)
                .orElse(new Rating());

        rating.setUser(user);
        rating.setEvent(event);
        rating.setValue(dto.value);

        ratingRepository.save(rating);
    }

    public RatingResponseDTO getRating(Long eventId, Principal principal) {
        Event event = getEventFromPrincipal(eventId);

        double averageRating = ratingRepository.findByEvent(event)
                .stream()
                .mapToInt(Rating::getValue)
                .average()
                .orElse(0.0);

        RatingResponseDTO ratingResponseDTO = new RatingResponseDTO();
        ratingResponseDTO.averageRating = averageRating;

        if(principal != null) {
            User user = getUserFromPrincipal(principal);
            ratingRepository.findByUserAndEvent(user, event)
                    .ifPresent(r->ratingResponseDTO.userRating = r.getValue());
        }

        return ratingResponseDTO;
    }

    private User getUserFromPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(()-> new RuntimeException("Nie znaleziono użytkownika"));
    }

    private Event getEventFromPrincipal(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Nie znaleziono wydarzenia"));
    }
}
