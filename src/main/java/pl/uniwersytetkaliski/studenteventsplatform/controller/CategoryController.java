package pl.uniwersytetkaliski.studenteventsplatform.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        CategoryResponseDTO created = categoryService.createCategory(categoryCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<Void> softDeleteCategory(@PathVariable Long id) {
        categoryService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        categoryService.updateCategory(id, categoryUpdateDTO);
        return ResponseEntity.noContent().build();
    }
}
