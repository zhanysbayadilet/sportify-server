package kz.sportify.administrationservice.service.impl;

import kz.sportify.administrationservice.mapper.TeamMapper;
import kz.sportify.administrationservice.model.Category;
import kz.sportify.administrationservice.model.Team;
import kz.sportify.administrationservice.model.User;
import kz.sportify.administrationservice.model.dto.TeamDTO;
import kz.sportify.administrationservice.repository.CategoryRepository;
import kz.sportify.administrationservice.repository.TeamRepository;
import kz.sportify.administrationservice.repository.UserRepository;
import kz.sportify.administrationservice.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements ITeamService {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TeamMapper teamMapper;

    @Override
    public TeamDTO saveTeam(TeamDTO teamDTO) {
        User admin = userRepository.getUserById(teamDTO.getAdminId()).orElse(null);
        Category category = categoryRepository.getCategoriesById(teamDTO.getCategoryId());
        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setCreatedAt(teamDTO.getCreatedAt());
        team.setAdmin(admin);
        team.setCategory(category);
        return teamMapper.entityToApi(teamRepository.saveAndFlush(team));
    }
}
