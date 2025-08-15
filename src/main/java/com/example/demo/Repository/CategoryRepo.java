package com.example.demo.Repository;

import com.example.demo.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findCategoryByCategoryId(Long categoryId);
    Category findByCategoryId(Long categoryId);
}
