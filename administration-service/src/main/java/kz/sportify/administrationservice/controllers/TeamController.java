package kz.sportify.administrationservice.controllers;

import kz.sportify.administrationservice.model.dto.TeamDTO;
import kz.sportify.administrationservice.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private ITeamService teamService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTeam(@RequestBody TeamDTO teamDTO) {
        try {
            if (teamDTO == null) {
                throw new IllegalArgumentException("TeamDTO cannot be null");
            }
            TeamDTO savedTeam = teamService.saveTeam(teamDTO);
            return ResponseEntity.ok(savedTeam);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving team: " + ex.getMessage());
        }
    }
}
