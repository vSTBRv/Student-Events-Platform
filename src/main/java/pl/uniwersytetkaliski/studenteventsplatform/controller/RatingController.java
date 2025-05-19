package pl.uniwersytetkaliski.studenteventsplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.uniwersytetkaliski.studenteventsplatform.repository.RatingRepository;

@RestController
@RequestMapping("/ap/events")
public class RatingController {
    private final RatingRepository ratingRepository;
    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        Double avg = ratingRepository.findAverageRatingForEvent(id);
        return ResponseEntity.ok(avg!=null?avg:0.0);
    }
}
