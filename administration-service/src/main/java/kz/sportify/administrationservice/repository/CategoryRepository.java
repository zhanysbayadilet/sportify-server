package kz.sportify.administrationservice.repository;

import kz.sportify.administrationservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getCategoriesById(Integer id);
}
