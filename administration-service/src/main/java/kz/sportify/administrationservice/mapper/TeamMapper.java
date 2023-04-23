package kz.sportify.administrationservice.mapper;

import kz.sportify.administrationservice.model.Team;
import kz.sportify.administrationservice.model.dto.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    @Mappings({})
    TeamDTO entityToApi(Team team);
    @Mappings({})
    Team apiToEntity(TeamDTO teamDTO);
}
