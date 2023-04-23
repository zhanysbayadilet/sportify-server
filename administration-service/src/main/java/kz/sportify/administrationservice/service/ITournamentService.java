package kz.sportify.administrationservice.service;

import kz.sportify.administrationservice.model.UserTournament;
import kz.sportify.administrationservice.model.dto.UserDTO;
import kz.sportify.administrationservice.util.DataPageDto;
import kz.sportify.administrationservice.util.SearchRequestDto;
import kz.sportify.administrationservice.model.dto.TournamentDTO;

import java.util.Map;

public interface ITournamentService {

    DataPageDto<TournamentDTO> getAllTournament(Map<String, String> params, SearchRequestDto searchRequestDto);

    TournamentDTO saveTournament(TournamentDTO tournamentDTO);

    Boolean deleteTournamentById(Long id);

    TournamentDTO getTournamentById(Long id);

    DataPageDto<UserDTO> getTournamentParticipants(Map<String, String> params);

    TournamentDTO addParticipant(Long user_id, Long tournament_id);

    Integer getCountTournaments();

    UserTournament checkStatusSubscribe(Long userId, Long tournamentId);

    Integer unsubscribeToTournament(Long userId, Long tournamentId);

    DataPageDto<TournamentDTO> getUserTournaments(Map<String, String> params);

    DataPageDto<TournamentDTO> getMyTournaments(Map<String, String> params);
}
