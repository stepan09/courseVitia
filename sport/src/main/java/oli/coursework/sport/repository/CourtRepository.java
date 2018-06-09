/*
 * Copyright by Stepan Oliinyk (c) 2018.
 */

package oli.coursework.sport.repository;

import oli.coursework.sport.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    List<Court> findByCompetitions_SportKind_IdAndCompetitions_StartDateBetween(Long sportKindId, Date firstDate,
                                                                                Date secondDate);
}
