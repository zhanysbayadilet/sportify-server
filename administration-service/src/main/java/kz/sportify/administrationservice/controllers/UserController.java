package kz.sportify.administrationservice.controllers;

import kz.sportify.administrationservice.model.dto.TournamentDTO;
import kz.sportify.administrationservice.repository.UserRepository;
import kz.sportify.administrationservice.service.ITournamentService;
import kz.sportify.administrationservice.util.DataPageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ITournamentService tournamentService;
    private final UserRepository userRepository;

    public UserController(ITournamentService tournamentService, UserRepository userRepository) {
        this.tournamentService = tournamentService;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userRepository.deleteById(id);
    }

    // Tournaments of this User

    @GetMapping("/tournaments")
    public ResponseEntity<DataPageDto<TournamentDTO>> getUserTournaments(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(tournamentService.getUserTournaments(params));
    }

    @GetMapping("/myTournaments")
    public ResponseEntity<DataPageDto<TournamentDTO>> getMyTournaments(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(tournamentService.getMyTournaments(params));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCountUsers(){
        return ResponseEntity.ok(userRepository.count());
    }
}
