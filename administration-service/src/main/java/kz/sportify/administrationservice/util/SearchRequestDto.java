package kz.sportify.administrationservice.util;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchRequestDto {
    private String searchText;
    private Date fromDate;
    private Date untilDate;
    private List<String> categories;
}
