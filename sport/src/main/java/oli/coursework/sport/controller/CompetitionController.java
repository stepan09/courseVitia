/*
 * Copyright by Stepan Oliinyk (c) 2018.
 */

package oli.coursework.sport.controller;

import oli.coursework.sport.exception.ResourceNotFoundException;
import oli.coursework.sport.model.Competition;
import oli.coursework.sport.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompetitionController {

    @Autowired
    private CompetitionRepository competitionRepository;

    /**
     *
     * @param organizerId
     * @return
     */
    @GetMapping("/competitions-by-organizer/{id}")
    public List<Competition> getCompetitionsByOrganizerId(@PathVariable(value = "id") Long organizerId){
        return competitionRepository.findByOrganizer_OrganizerId(organizerId);
    }

    /**
     *
     * @param sportKindId
     * @return
     */
    @GetMapping("/competitions-by-sport-kind/{id}")
    public List<Competition> getCompetitionsBySportKindId(@PathVariable(value = "id") Long sportKindId){
        return competitionRepository.findBySportKind_Id(sportKindId);
    }

    /**
     *
     * @param sportKindId
     * @param gymId
     * @return
     */
    @GetMapping("/competitions-by-sport-kind-gym/{sportKindId}/{gymId}")
    public List<Competition> getCompetitionsBySportKindIdAndGymId(@PathVariable(value = "sportKindId") Long sportKindId,
                                                                  @PathVariable(value = "gymId") Long gymId){
        return competitionRepository.findCompetitionsBySportKind_IdAndGyms_GymId(sportKindId,gymId);
    }

    /**
     *
     * @param sportKindId
     * @param courtId
     * @return
     */
    @GetMapping("/competitions-by-sport-kind-court/{sportKindId}/{courtId}")
    public List<Competition> getCompetitionsBySportKindIdAndCourtId(@PathVariable(value = "sportKindId") Long sportKindId,
                                                                    @PathVariable(value = "courtId") Long courtId){
        return competitionRepository.findCompetitionsBySportKind_IdAndCourts_CourtId(sportKindId,courtId);
    }

    /**
     *
     * @param sportKindId
     * @param stadiumId
     * @return
     */
    @GetMapping("/competitions-by-sport-kind-stadium/{sportKindId}/{stadiumId}")
    public List<Competition> getCompetitionsBySportKindIdAndStadiumId(@PathVariable(value = "sportKindId") Long sportKindId,
                                                                      @PathVariable(value = "stadiumId") Long stadiumId){
        return competitionRepository.findCompetitionsBySportKind_IdAndStadiums_StadiumId(sportKindId, stadiumId);
    }

    /**
     *
     * @param sportKindId
     * @param organizerId
     * @return
     */
    @GetMapping("/competitions-by-sport-kind-organizer/{sportKindId}/{organizerId}")
    public List<Competition> getCompetitionsBySportKindIdAndOrganizerId(@PathVariable(value = "sportKindId") Long sportKindId,
                                                                        @PathVariable(value = "organizerId") Long organizerId){
        return competitionRepository.findCompetitionsBySportKind_IdAndOrganizer_OrganizerId(sportKindId, organizerId);
    }

    /**
     *
     * @param organizerId
     * @return
     */
    @GetMapping("/competition-count/{id}")
    public Long getCompetitionsCountByOrganizer(@PathVariable(value = "id") Long organizerId) {
        return competitionRepository.countByOrganizer_OrganizerId(organizerId);
    }

    /**
     *
     * @return
     */
    @GetMapping("/competitions")
    public List<Competition> getAllCompetitions(){
        return competitionRepository.findAll();
    }

    /**
     *
     * @param competition
     * @return
     */
    @PostMapping("/competitions")
    public Competition createCompetition(@Valid @RequestBody Competition competition) {
        return competitionRepository.save(competition);
    }

    /**
     *
     * @param competitionId
     * @return
     */
    @GetMapping("/competitions/{id}")
    public Competition getCompetitionById(@PathVariable(value = "id") Long competitionId) {
        return competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym", "id", competitionId));
    }

    /**
     *
     * @param competitionId
     * @param competitionDetails
     * @return
     */
    @PutMapping("/competitions/{id}")
    public Competition updateCompetition(@PathVariable(value = "id") Long competitionId,
                                         @Valid @RequestBody Competition competitionDetails) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym", "id", competitionId));

        competition.setName(competitionDetails.getName());
        competition.setStartDate(competitionDetails.getStartDate());
        competition.setFinishDate(competitionDetails.getFinishDate());
        competition.setOrganizer(competitionDetails.getOrganizer());
        competition.setSportKind(competitionDetails.getSportKind());
        competition.setSportsmen(competitionDetails.getSportsmen());
        competition.setStadiums(competitionDetails.getStadiums());
        competition.setCourts(competitionDetails.getCourts());
        competition.setGyms(competitionDetails.getGyms());

        Competition updateCompetition = competitionRepository.save(competition);

        return updateCompetition;
    }

    /**
     *
     * @param competitionId
     * @return
     */
    @DeleteMapping("/competitions/{id}")
    public ResponseEntity<?> deleteCompetition(@PathVariable(name = "id") Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Gym", "id", competitionId));

        competitionRepository.delete(competition);

        return ResponseEntity.ok().build();
    }

    /**
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    @GetMapping("/competitions/{first-date}/{second-date}")
    public List <Competition> getCompetitionByDate(@PathVariable(value = "first-date")
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDate,
                                                   @PathVariable(value = "second-date")
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd") Date secondDate) {
        return competitionRepository.findByStartDateBetween(firstDate, secondDate);
    }
}
