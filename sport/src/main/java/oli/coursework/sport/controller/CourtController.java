/*
 * Copyright by Stepan Oliinyk (c) 2018.
 */

package oli.coursework.sport.controller;

import oli.coursework.sport.exception.ResourceNotFoundException;
import oli.coursework.sport.model.Court;
import oli.coursework.sport.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CourtController {

    @Autowired
    private CourtRepository courtRepository;

    /**
     *
     * @return
     */
    @GetMapping("/courts")
    public List<Court> getAllCourts(){
        return courtRepository.findAll();
    }

    /**
     *
     * @param court
     * @return
     */
    @PostMapping("/courts")
    public Court createCourt(@Valid @RequestBody Court court) {
        return courtRepository.save(court);
    }

    /**
     *
     * @param courtId
     * @return
     */
    @GetMapping("/courts/{id}")
    public Court getCourtById(@PathVariable(value = "id") Long courtId) {
        return courtRepository.findById(courtId)
                .orElseThrow(() -> new ResourceNotFoundException("Court", "id", courtId));
    }

    @GetMapping("/courts/{id}/{first-date}/{second-date}")
    public List <Court> getStadiumsBySportKindAndDate(@PathVariable(value = "id") Long sportKindId,
                                                        @PathVariable(value = "first-date")
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDate,
                                                        @PathVariable(value = "second-date")
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date secondDate) {
        return courtRepository
                .findByCompetitions_SportKind_IdAndCompetitions_StartDateBetween(sportKindId, firstDate, secondDate);
    }

    /**
     *
     * @param courtId
     * @param courtDetails
     * @return
     */
    @PutMapping("/courts/{id}")
    public Court updateCourt(@PathVariable(value = "id") Long courtId,
                              @Valid @RequestBody Court courtDetails) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new ResourceNotFoundException("Court", "id", courtId));

        court.setName(courtDetails.getName());
        court.setAddress(courtDetails.getAddress());
        court.setCoverType(courtDetails.getCoverType());
        court.setFoundationDate(courtDetails.getFoundationDate());

        Court updateCourt = courtRepository.save(court);

        return updateCourt;
    }

    /**
     *
     * @param courtId
     * @return
     */
    @DeleteMapping("/courts/{id}")
    public ResponseEntity<?> deleteCourt(@PathVariable(value = "id") Long courtId) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new ResourceNotFoundException("Court", "id", courtId));

        courtRepository.delete(court);

        return ResponseEntity.ok().build();
    }
}
