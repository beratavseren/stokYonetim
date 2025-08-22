package com.example.demo.Controller;

import com.example.demo.Dto.Brand.CategoryDto;
import com.example.demo.Dto.Category.AddCategoryDto;
import com.example.demo.Dto.Category.UpdateCategoryDto;
import com.example.demo.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService=categoryService;
    }

    @PostMapping("/add")
    public boolean addCategory(@RequestBody AddCategoryDto addCategoryDto)
    {
        return categoryService.addCategory(addCategoryDto);
    }

    @PutMapping("/update")
    public boolean updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto)
    {
        return categoryService.updateCategory(updateCategoryDto);
    }

    @DeleteMapping("/delete/{categoryId}")
    public boolean deleteCategory(@PathVariable Long categoryId)
    {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/getCategory/{categoryId}")
    public CategoryDto getCategory(@PathVariable Long categoryId)
    {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/getCategories")
    public List<CategoryDto> getCategories()
    {
        return categoryService.getCategories();
    }
}
