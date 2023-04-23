package kz.sportify.administrationservice.service;


import kz.sportify.administrationservice.util.DataPageDto;
import kz.sportify.administrationservice.model.dto.CategoryDTO;

import java.util.Map;

public interface ICategoryService {
    DataPageDto<CategoryDTO> getAllCategory(Map<String, String> params);

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    Boolean deleteCategoryById(int id);
}
