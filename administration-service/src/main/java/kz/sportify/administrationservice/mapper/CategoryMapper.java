package kz.sportify.administrationservice.mapper;


import kz.sportify.administrationservice.model.Category;
import kz.sportify.administrationservice.model.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({})
    List<CategoryDTO> CategoryListToCategoryDTOList(List<Category> categoryList);
    @Mappings({})
    List<Category> CategoryDTOListToCategoryList(List<CategoryDTO> categoryDTOList);
    @Mappings({})
    CategoryDTO entityToApi(Category category);
    @Mappings({})
    Category apiToEntity(CategoryDTO categoryDTO);
}
