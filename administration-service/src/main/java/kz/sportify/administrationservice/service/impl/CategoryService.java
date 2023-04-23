package kz.sportify.administrationservice.service.impl;

import kz.sportify.administrationservice.mapper.CategoryMapper;
import kz.sportify.administrationservice.model.Category;
import kz.sportify.administrationservice.repository.CategoryRepository;
import kz.sportify.administrationservice.model.dto.CategoryDTO;
import kz.sportify.administrationservice.service.ICategoryService;
import kz.sportify.administrationservice.util.DataPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public DataPageDto<CategoryDTO> getAllCategory(Map<String, String> params) {
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
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> resultPage = categoryRepository.findAll(pageable);

        DataPageDto<CategoryDTO> dataPageDto = new DataPageDto<>();
        dataPageDto.setItems(resultPage.getContent().stream().map(categoryMapper::entityToApi).collect(Collectors.toList()));
        dataPageDto.setPage(resultPage.getNumber());
        dataPageDto.setSize(resultPage.getSize());
        dataPageDto.setTotal(resultPage.getTotalElements());
        return dataPageDto;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.apiToEntity(categoryDTO);
        return categoryMapper.entityToApi(categoryRepository.saveAndFlush(category));
    }

    @Override
    public Boolean deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
        return true;
    }

}
