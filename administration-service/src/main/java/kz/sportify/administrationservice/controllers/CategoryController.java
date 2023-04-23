package kz.sportify.administrationservice.controllers;

import kz.sportify.administrationservice.model.dto.CategoryDTO;
import kz.sportify.administrationservice.service.ICategoryService;
import kz.sportify.administrationservice.util.DataPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<DataPageDto<CategoryDTO>> getAllCategory(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(categoryService.getAllCategory(params));
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.saveCategory(categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }

}
