package kz.sportify.administrationservice.model.dto;

import kz.sportify.administrationservice.model.City;
import kz.sportify.administrationservice.model.enums.ETournamentStatus;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TournamentDTO {
    private Long id;
    private String name;
    private String subtitle;
    private String description;
    private Date startDate;
    private ETournamentStatus status;
    private City city;
    private String location;
    private Date endDate;
    private int prizeFund;
    private CategoryDTO category;
    private UserDTO organizer;
}
