package com.example.demo.Repository;

import com.example.demo.Entity.Brand;
import com.example.demo.Entity.BrandCategory;
import com.example.demo.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BrandCategoryRepo extends JpaRepository<BrandCategory,Long> {
    List<BrandCategory> findBrandCategoriesByBrand(Brand brand);
    List<BrandCategory> findBrandCategoriesByCategory(Category category);
    void deleteBrandCategoriesByBrand(Brand brand);
}
