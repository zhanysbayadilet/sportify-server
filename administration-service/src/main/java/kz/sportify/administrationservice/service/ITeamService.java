package kz.sportify.administrationservice.service;

import kz.sportify.administrationservice.model.dto.TeamDTO;

public interface ITeamService {
    TeamDTO saveTeam(TeamDTO teamDTO);
}
