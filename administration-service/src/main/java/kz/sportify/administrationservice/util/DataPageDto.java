package kz.sportify.administrationservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataPageDto<T> {
    private List<T> items;
    private int size;
    private int page;
    private Long total;
}

