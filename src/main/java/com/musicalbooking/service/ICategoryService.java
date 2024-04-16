package com.musicalbooking.service;

import com.musicalbooking.dto.CategoryDto;
import com.musicalbooking.entity.Category;
import com.musicalbooking.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICategoryService {

    CategoryDto getCategoryById(Long id) throws ResourceNotFoundException;

    List<CategoryDto> getCategories();

    CategoryDto postCategory(Category category);

    CategoryDto updateCategory(Category category);

    String deleteCategoryById(Long id) throws ResourceNotFoundException;
}
