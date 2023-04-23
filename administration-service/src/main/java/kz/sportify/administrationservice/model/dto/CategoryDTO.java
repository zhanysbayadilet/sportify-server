package kz.sportify.administrationservice.model.dto;

import kz.sportify.administrationservice.model.enums.ECategoryType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryDTO {
    private int id;
    private String kkName;
    private String ruName;
    private String enName;
    private ECategoryType type;
    private String img;
    private String description;
}
