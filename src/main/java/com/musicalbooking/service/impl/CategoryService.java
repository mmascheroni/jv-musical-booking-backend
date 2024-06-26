package com.musicalbooking.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicalbooking.dto.CategoryDto;
import com.musicalbooking.entity.Category;
import com.musicalbooking.entity.Product;
import com.musicalbooking.exceptions.BadRequestException;
import com.musicalbooking.exceptions.ResourceNotFoundException;
import com.musicalbooking.repository.CategoryRepository;
import com.musicalbooking.repository.ProductRepository;
import com.musicalbooking.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CategoryDto getCategoryById(Long id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id).orElse(null);
        CategoryDto categoryDto = null;

        if ( category != null ) {
            categoryDto = objectMapper.convertValue(category, CategoryDto.class);
            log.info("The category with id {} has been found: {}", id, categoryDto);

            return categoryDto;
        } else {
            log.error("The category with id {} was not found", id);
            throw new ResourceNotFoundException("Not found the category with id: " + id);
        }
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoriesDto = null;

        if ( categories != null  ) {
            categoriesDto = categories.stream()
                    .map(category -> objectMapper.convertValue(category, CategoryDto.class))
                    .collect(Collectors.toList());
            log.info("All these categories were found: {}", categoriesDto);

            return categoriesDto;
        }

        log.warn("No registered categories found");
        return categoriesDto;
    }

    @Override
    public CategoryDto postCategory(Category category) {
        Category categoryToPersist = categoryRepository.save(category);
        CategoryDto categoryDto = objectMapper.convertValue(categoryToPersist, CategoryDto.class);
        log.info("Category registered successfully: {}", categoryDto);

        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(Category category) {
        return null;
    }

    @Override
    public String deleteCategoryById(Long id) throws ResourceNotFoundException, BadRequestException {
        if ( getCategoryById(id) != null ) {
            List<Product> products = productRepository.findByCategoryId(id);

            if ( products.isEmpty() ) {
                categoryRepository.deleteById(id);
                log.warn("The category with id {} has been delete", id);
            } else {
                throw new BadRequestException("Cannot delete category with id " + id
                        + " because it is associated with one or more products.");
            }
        }

        return "The category has been removed successfully";
    }
}
