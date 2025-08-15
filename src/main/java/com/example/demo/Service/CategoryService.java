package com.example.demo.Service;

import com.example.demo.Dto.Brand.CategoryDto;
import com.example.demo.Dto.Category.AddCategoryDto;
import com.example.demo.Dto.Category.UpdateCategoryDto;
import com.example.demo.Entity.BrandCategory;
import com.example.demo.Entity.Category;
import com.example.demo.Repository.BrandCategoryRepo;
import com.example.demo.Repository.CategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    CategoryRepo categoryRepo;
    BrandCategoryRepo brandCategoryRepo;

    @Autowired
    public CategoryService(CategoryRepo caregoryRepo, BrandCategoryRepo brandCategoryRepo)
    {
        this.brandCategoryRepo=brandCategoryRepo;
        this.categoryRepo=caregoryRepo;
    }

    @Transactional
    public boolean addCategory(AddCategoryDto addCategoryDto)
    {
        try {
            Category category = new Category(addCategoryDto.getCategoryName());
            categoryRepo.save(category);
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean updateCategory(UpdateCategoryDto updateCategoryDto)
    {
        try {
            Category category = categoryRepo.findCategoryByCategoryId(updateCategoryDto.getCategoryId());
            category.setCategoryName(updateCategoryDto.getCategoryName());
            categoryRepo.save(category);
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean deleteCategory(Long categoryId)
    {
        try {
            Category category = categoryRepo.findCategoryByCategoryId(categoryId);
            List<BrandCategory> brandCategories = brandCategoryRepo.findBrandCategoriesByCategory(category);

            if (brandCategories.isEmpty())
            {
                categoryRepo.delete(category);
                return true;
            }else {
                return false;
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public CategoryDto getCategory(Long categoryId)
    {
        try {
            Category category = categoryRepo.findCategoryByCategoryId(categoryId);
            return new CategoryDto(category.getCategoryId(), category.getCategoryName());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new CategoryDto();
        }
    }

    @Transactional
    public List<CategoryDto> getCategories()
    {
        try {

            List<Category> categories = categoryRepo.findAll();
            List<CategoryDto> categoryDtos = new ArrayList<>();

            for (Category category:categories)
            {
                categoryDtos.add(new CategoryDto(category.getCategoryId(), category.getCategoryName()));
            }

            return categoryDtos;

        }catch (Exception e) {

            System.out.println(e.getMessage());
            return new ArrayList<>();

        }
    }

}
