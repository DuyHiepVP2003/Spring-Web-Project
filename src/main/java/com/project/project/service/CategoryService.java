package com.project.project.service;

import com.project.project.model.Category;
import com.project.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    public Optional<Category> findCategoryById(Long id){
        return categoryRepository.findById(id);
    }
    public void save(Category category){
        categoryRepository.save(category);
    }
    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }
}
