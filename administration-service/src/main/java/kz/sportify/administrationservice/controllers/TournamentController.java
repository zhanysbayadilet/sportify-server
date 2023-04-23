package kz.sportify.administrationservice.controllers;

import kz.sportify.administrationservice.model.City;
import kz.sportify.administrationservice.model.UserTournament;
import kz.sportify.administrationservice.model.dto.UserDTO;
import kz.sportify.administrationservice.model.dto.TournamentDTO;
import kz.sportify.administrationservice.repository.CityRepository;
import kz.sportify.administrationservice.service.impl.TournamentService;
import kz.sportify.administrationservice.util.DataPageDto;
import kz.sportify.administrationservice.util.SearchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/tournament")
public class TournamentController {

    private final TournamentService tournamentService;
    private final CityRepository cityRepository;

    @Autowired
    public TournamentController(TournamentService tournamentService,
                                CityRepository cityRepository) {
        this.tournamentService = tournamentService;
        this.cityRepository = cityRepository;
    }

    @PostMapping("/all")
    public ResponseEntity<DataPageDto<TournamentDTO>> getAllTournament(@RequestParam Map<String, String> params,
                                                                       @RequestBody SearchRequestDto searchRequestDto){
        return ResponseEntity.ok(tournamentService.getAllTournament(params, searchRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable String id) {
        return ResponseEntity.ok(tournamentService.getTournamentById(Long.valueOf(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTournament(@RequestBody TournamentDTO tournamentDTO) {
        return ResponseEntity.ok(tournamentService.saveTournament(tournamentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTournamentById(@PathVariable Long id){
        return ResponseEntity.ok(tournamentService.deleteTournamentById(id));
    }

    //Tournament Participants

    @GetMapping("/participants")
    public ResponseEntity<DataPageDto<UserDTO>> getTournamentParticipants(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(tournamentService.getTournamentParticipants(params));
    }

    @PostMapping("/{user_id}/{tournament_id}")
    public ResponseEntity<?> addParticipant(@PathVariable Long user_id, @PathVariable Long tournament_id) {
        return ResponseEntity.ok(tournamentService.addParticipant(user_id, tournament_id));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCountTournaments(){
        return ResponseEntity.ok(tournamentService.getCountTournaments());
    }

    @GetMapping("/userId/{userId}/tournamentId/{tournamentId}")
    public ResponseEntity<UserTournament> checkStatusSubscribe(@PathVariable Long userId,
                                                               @PathVariable Long tournamentId){
        return ResponseEntity.ok(tournamentService.checkStatusSubscribe(userId, tournamentId));
    }

    @DeleteMapping("/userId/{userId}/tournamentId/{tournamentId}/unsubscribe")
    public ResponseEntity<Integer> unsubscribeToTournament(@PathVariable Long userId, @PathVariable Long tournamentId){
        return ResponseEntity.ok(tournamentService.unsubscribeToTournament(userId, tournamentId));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityRepository.findAll());
    }
}

