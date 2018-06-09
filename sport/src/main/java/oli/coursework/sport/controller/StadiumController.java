/*
 * Copyright by Stepan Oliinyk (c) 2018.
 */

package oli.coursework.sport.controller;

import oli.coursework.sport.exception.ResourceNotFoundException;
import oli.coursework.sport.model.Stadium;
import oli.coursework.sport.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StadiumController {

    @Autowired
    private StadiumRepository stadiumRepository;

    /**
     *
     * @return
     */
    @GetMapping("/stadiums")
    public List<Stadium> getAllStadiums(){
        return stadiumRepository.findAll();
    }

    /**
     *
     * @return
     */
    @GetMapping("/stadiums-treadmill")
    public List<Stadium> getAllStadiumsWithTreadmill(){
        return stadiumRepository.findAllByTreadmillTrue();
    }

    /**
     *
     * @param capacity
     * @return
     */
    @GetMapping("/stadiums-greater/{capacity}")
    public List<Stadium> getAllStadiumsGreaterThan(@PathVariable(value = "capacity") Integer capacity){
        return stadiumRepository.findAllByCapacityGreaterThan(capacity);
    }

    /**
     *
     * @return
     */
    @GetMapping("/stadiums-order-desc")
    public List<Stadium> getAllStadiumsOrderDescCapacity(){
        return stadiumRepository.findByOrderByCapacityDesc();
    }

    /**
     *
     * @param sportKindId
     * @param firstDate
     * @param secondDate
     * @return
     */
    @GetMapping("/stadiums/{id}/{first-date}/{second-date}")
    public List <Stadium> getStadiumsBySportKindAndDate(@PathVariable(value = "id") Long sportKindId,
                                                        @PathVariable(value = "first-date")
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDate,
                                                        @PathVariable(value = "second-date")
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date secondDate) {
        return stadiumRepository
                .findByCompetitions_SportKind_IdAndCompetitions_StartDateBetween(sportKindId, firstDate, secondDate);
    }

    /**
     *
     * @return
     */
    @GetMapping("/stadiums-order-asc")
    public List<Stadium> getAllStadiumsOrderAscCapacity(){
        return stadiumRepository.findByOrderByCapacityAsc();
    }

    /**
     *
     * @param stadium
     * @return
     */
    @PostMapping("/stadiums")
    public Stadium createStadium(@Valid @RequestBody Stadium stadium){
        return stadiumRepository.save(stadium);
    }

    public Stadium getStadiumById(@PathVariable(value = "id") Long stadiumId) {
        return stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new ResourceNotFoundException("Stadium", "id", stadiumId));
    }

    /**
     *
     * @param stadiumId
     * @param stadiumDetails
     * @return
     */
    @PutMapping("/stadiums/{id}")
    public Stadium updateStadium(@PathVariable(value = "id") Long stadiumId,
                             @Valid @RequestBody Stadium stadiumDetails) {
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new ResourceNotFoundException("Stadium", "id", stadiumId));

        stadium.setName(stadiumDetails.getName());
        stadium.setFoundationDate(stadiumDetails.getFoundationDate());
        stadium.setAddress(stadiumDetails.getAddress());
        stadium.setCapacity(stadiumDetails.getCapacity());
        stadium.setTreadmill(stadiumDetails.isTreadmill());

        Stadium updateStadium = stadiumRepository.save(stadium);

        return updateStadium;
    }

    /**
     *
     * @param stadiumId
     * @return
     */
    @DeleteMapping("/stadiums/{id}")
    public ResponseEntity<?> deleteStadium(@PathVariable(value = "id") Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new ResourceNotFoundException("Stadium", "id", stadiumId));

        stadiumRepository.delete(stadium);

        return ResponseEntity.ok().build();
    }
}
