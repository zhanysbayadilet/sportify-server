package kz.sportify.administrationservice.service.impl;

import kz.sportify.administrationservice.mapper.TournamentMapper;
import kz.sportify.administrationservice.mapper.UserMapper;
import kz.sportify.administrationservice.model.Category;
import kz.sportify.administrationservice.model.Tournament;
import kz.sportify.administrationservice.model.User;
import kz.sportify.administrationservice.model.UserTournament;
import kz.sportify.administrationservice.model.dto.UserDTO;
import kz.sportify.administrationservice.repository.UserTournamentRepository;
import kz.sportify.administrationservice.model.dto.TournamentDTO;
import kz.sportify.administrationservice.model.enums.ETournamentStatus;
import kz.sportify.administrationservice.repository.TournamentRepository;
import kz.sportify.administrationservice.repository.UserRepository;
import kz.sportify.administrationservice.service.ITournamentService;
import kz.sportify.administrationservice.util.DataPageDto;
import kz.sportify.administrationservice.util.SearchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TournamentService implements ITournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final UserTournamentRepository userTournamentRepository;
    private final TournamentMapper tournamentMapper;
    private final UserMapper userMapper;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository,
                             UserRepository userRepository,
                             UserTournamentRepository userTournamentRepository,
                             TournamentMapper tournamentMapper,
                             UserMapper userMapper) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.userTournamentRepository = userTournamentRepository;
        this.tournamentMapper = tournamentMapper;
        this.userMapper = userMapper;
    }

    public DataPageDto<TournamentDTO> getAllTournament(Map<String, String> params, SearchRequestDto searchRequestDto){
        int pageNumber = 1;
        int pageSize = 10;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "pageNumber":
                    pageNumber = Integer.parseInt(entry.getValue());
                    break;
                case "pageSize":
                    pageSize = Integer.parseInt(entry.getValue());
                    break;
            }
        }

        boolean noCriteria = searchRequestDto == null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Tournament> resultPage = noCriteria ? tournamentRepository.findAll(pageable) :
                tournamentRepository.findAll(buildSpecificationTournament(searchRequestDto), pageable);

        DataPageDto<TournamentDTO> dataPageDto = new DataPageDto<>();
        dataPageDto.setItems(resultPage.getContent().stream().map(tournamentMapper::entityToApi).collect(Collectors.toList()));
        dataPageDto.setPage(resultPage.getNumber());
        dataPageDto.setSize(resultPage.getSize());
        dataPageDto.setTotal(resultPage.getTotalElements());
        return dataPageDto;
    }

    public Specification<Tournament> buildSpecificationTournament(SearchRequestDto searchRequestDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchRequestDto.getSearchText() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + searchRequestDto.getSearchText().toLowerCase() + "%"));
            }

            if (searchRequestDto.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), searchRequestDto.getFromDate()));
            }

            if (searchRequestDto.getUntilDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), searchRequestDto.getUntilDate()));
            }

            if (searchRequestDto.getCategories() != null) {
                Join<Tournament, Category> categoryJoin = root.join("category", JoinType.LEFT);
                predicates.add(categoryJoin.get("name").in(searchRequestDto.getCategories()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public TournamentDTO saveTournament(TournamentDTO tournamentDTO) {
        if (tournamentDTO.getId() == null){
            tournamentDTO.setStatus(ETournamentStatus.OPEN);
        }
        return tournamentMapper.entityToApi(tournamentRepository.saveAndFlush(tournamentMapper.apiToEntity(tournamentDTO)));
    }

    @Override
    public Boolean deleteTournamentById(Long id) {
        System.out.println(id + " " + tournamentRepository.findById(id));
        tournamentRepository.deleteById(id);
        return null;
    }

    @Override
    public TournamentDTO getTournamentById(Long id) {
        try {
            return tournamentMapper.entityToApi(tournamentRepository.getTournamentById(id).orElseThrow(ChangeSetPersister.NotFoundException::new));
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataPageDto<UserDTO> getTournamentParticipants(Map<String, String> params) {
        int pageNumber = 1;
        int pageSize = 10;
        String tournamentId = "";

        for (Map.Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "pageNumber":
                    pageNumber = Integer.parseInt(entry.getValue());
                    break;
                case "pageSize":
                    pageSize = Integer.parseInt(entry.getValue());
                    break;
                case "tournamentId":
                    tournamentId = entry.getValue();
                    break;
            }
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> resultPage  = userRepository.findAllByParams(tournamentId, pageable);

        DataPageDto<UserDTO> dataPageDto = new DataPageDto<>();
        dataPageDto.setItems(resultPage.getContent().stream().map(userMapper::entityToApi).collect(Collectors.toList()));
        dataPageDto.setPage(resultPage.getNumber());
        dataPageDto.setSize(resultPage.getSize());
        dataPageDto.setTotal(resultPage.getTotalElements());
        return dataPageDto;
    }

    @Override
    public TournamentDTO addParticipant(Long user_id, Long tournament_id) {
        Tournament tournament = tournamentRepository.getTournamentById(tournament_id).orElse(null);
        if(tournament == null)
            return null;

        User user = userRepository.getUserById(user_id).orElse(null);
        if(user == null)
            return null;

        tournament.addUser(user);
        return tournamentMapper.entityToApi(tournamentRepository.saveAndFlush(tournament));
    }

    @Override
    public Integer getCountTournaments() {
        return Math.toIntExact(tournamentRepository.count());
    }

    @Override
    public UserTournament checkStatusSubscribe(Long userId, Long tournamentId) {
        return userTournamentRepository.findByUserIdAndTournamentId(userId, tournamentId);
    }

    @Override
    public Integer unsubscribeToTournament(Long userId, Long tournamentId) {
        return userTournamentRepository.deleteUserTournamentByUserIdAndTournamentId(userId, tournamentId);
    }

    @Override
    public DataPageDto<TournamentDTO> getUserTournaments(Map<String, String> params) {
        int pageNumber = 1;
        int pageSize = 10;
        String userId = "";

        for (Map.Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "pageNumber":
                    pageNumber = Integer.parseInt(entry.getValue());
                    break;
                case "pageSize":
                    pageSize = Integer.parseInt(entry.getValue());
                    break;
                case "userId":
                    userId = entry.getValue();
                    break;
            }
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Tournament> resultPage  = tournamentRepository.findAllUserTournaments(userId, pageable);

        DataPageDto<TournamentDTO> dataPageDto = new DataPageDto<>();
        dataPageDto.setItems(resultPage.getContent().stream().map(tournamentMapper::entityToApi).collect(Collectors.toList()));
        dataPageDto.setPage(resultPage.getNumber());
        dataPageDto.setSize(resultPage.getSize());
        dataPageDto.setTotal(resultPage.getTotalElements());
        return dataPageDto;
    }

    @Override
    public DataPageDto<TournamentDTO> getMyTournaments(Map<String, String> params) {
        int pageNumber = 1;
        int pageSize = 10;
        String userId = "";

        for (Map.Entry<String, String> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "pageNumber":
                    pageNumber = Integer.parseInt(entry.getValue());
                    break;
                case "pageSize":
                    pageSize = Integer.parseInt(entry.getValue());
                    break;
                case "userId":
                    userId = entry.getValue();
                    break;
            }
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Tournament> resultPage  = tournamentRepository.findAllByOrganizerId(userId, pageable);

        DataPageDto<TournamentDTO> dataPageDto = new DataPageDto<>();
        dataPageDto.setItems(resultPage.getContent().stream().map(tournamentMapper::entityToApi).collect(Collectors.toList()));
        dataPageDto.setPage(resultPage.getNumber());
        dataPageDto.setSize(resultPage.getSize());
        dataPageDto.setTotal(resultPage.getTotalElements());
        return dataPageDto;
    }


}
