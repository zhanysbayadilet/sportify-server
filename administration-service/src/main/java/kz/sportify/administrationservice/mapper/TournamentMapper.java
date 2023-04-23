package kz.sportify.administrationservice.mapper;

import kz.sportify.administrationservice.model.Tournament;
import kz.sportify.administrationservice.model.dto.TournamentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentMapper {
    @Mappings({})
    List<TournamentDTO> TournamentListToTournamentDTOList(List<Tournament> tournamentList);
    @Mappings({})
    List<Tournament> TournamentDTOListToTournamentList(List<TournamentDTO> tournamentDTOList);
    @Mappings({})
    TournamentDTO entityToApi(Tournament tournament);
    @Mappings({})
    Tournament apiToEntity(TournamentDTO tournamentDTO);
}
