package kz.sportify.administrationservice.repository;

import kz.sportify.administrationservice.model.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Optional<Tournament> getTournamentById(Long id);

    Page<Tournament> findAll(Specification<Tournament> buildSpecificationTournament, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from _tournament t " +
            "left join _user_tournament ut on t.id = ut.tournament_id where ut.user_id = cast(:userId as bigint)")
    Page<Tournament> findAllUserTournaments(String userId, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from _tournament where organizer_id = cast(:userId as bigint)")
    Page<Tournament> findAllByOrganizerId(String userId, Pageable pageable);
}
