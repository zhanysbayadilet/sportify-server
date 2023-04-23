package kz.sportify.administrationservice.repository;

import kz.sportify.administrationservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> getUserById(Long id);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

//	@Query(nativeQuery = true, value = "select count(id) from _users")
//	Integer getCountUsers();

	@Query(nativeQuery = true, value = "" +
			"select *\n" +
			"from _users u\n" +
			"         left join _user_tournament ut on u.id = ut.user_id\n" +
			"where tournament_id = cast(:tournamentId as bigint)\n")
	Page<User> findAllByParams(String tournamentId, Pageable pageable);
}
