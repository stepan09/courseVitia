package oli.coursework.sport.controller;

import oli.coursework.sport.exception.ResourceNotFoundException;
import oli.coursework.sport.model.Coach;
import oli.coursework.sport.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CoachController {

    @Autowired
    private CoachRepository coachRepository;

    private static Logger log = Logger.getLogger(CoachController.class.getName());
    /**
     *
     * @param sportsmanId
     * @return
     */
    @GetMapping("/coaches-by-sportsman/{id}")
    public List<Coach> getCoachesBySportsmanId(@PathVariable(value = "id") Long sportsmanId){
        return coachRepository.findBySportsmen_SportsmanId(sportsmanId);
    }

    /**
     *
     * @return
     */
    @GetMapping("/coaches")
    public List<Coach> getAllCoaches() {return coachRepository.findAll();}

    /**
     *
     * @param coach
     * @return
     */
    @PostMapping("/coaches")
    public Coach createCoach(@Valid @RequestBody Coach coach) {
        return coachRepository.save(coach);
    }

    /**
     *
     * @param coachId
     * @return
     */
    @GetMapping("/coaches/{id}")
    public Coach getCoachById(@PathVariable(value = "id") Long coachId){
        return coachRepository.findById(coachId)
                .orElseThrow(() -> new ResourceNotFoundException("Coach", "id", coachId));
    }

    /**
     *
     * @param coachId
     * @param coachDetails
     * @return
     */
    @PutMapping("/coaches/{id}")
    public Coach updateCoach(@PathVariable(value = "id") Long coachId,
                             @Valid @RequestBody Coach coachDetails) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new ResourceNotFoundException("Coach", "id", coachId));

        log.info(coachDetails.getLastName());
        coach.setLastName(coachDetails.getLastName());
        coach.setFirstName(coachDetails.getFirstName());
        coach.setMiddleName(coachDetails.getMiddleName());
        coach.setBirthDate(coachDetails.getBirthDate());
        coach.setSportsmen(coachDetails.getSportsmen());

        Coach updateCoach = coachRepository.save(coach);

        log.info(updateCoach.getLastName());
        return updateCoach;
    }

    /**
     *
     * @param coachId
     * @return
     */
    @DeleteMapping("/coaches/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable(value = "id") Long coachId) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new ResourceNotFoundException("Coach", "id", coachId));

        coachRepository.delete(coach);

        return ResponseEntity.ok().build();
    }

}
