package com.example.quanlysach.Controller;
import com.example.quanlysach.Service.CategoryService;
import com.example.quanlysach.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public Category createCategory(@RequestPart("category") Category category) throws IOException {
        return categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestPart("category") Category categoryDetails) throws IOException {
        Category category = categoryService.getCategoryById(id);
        category.setName(categoryDetails.getName());

        final Category updatedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().build();
    }
}
