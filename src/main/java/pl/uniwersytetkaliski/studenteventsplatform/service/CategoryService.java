package pl.uniwersytetkaliski.studenteventsplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CategoryResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CreateCategoryDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.UpdateCategoryDTO;
import pl.uniwersytetkaliski.studenteventsplatform.mapper.CategoryMapper;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;
import pl.uniwersytetkaliski.studenteventsplatform.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategory(Long id) {
        return categoryMapper.toDTO(
                categoryRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException("Category not found")
                )
        );
    }

    public CategoryResponseDTO createCategory(CreateCategoryDTO createCategoryDTO) {
        Category saved = categoryRepository.save(categoryMapper.toEntity(createCategoryDTO));
        return categoryMapper.toDTO(saved);
    }

    public CategoryResponseDTO updateCategory(Long id,UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Category not found"));
        category.setName(updateCategoryDTO.getName());
        Category updated = categoryRepository.save(category);
        return categoryMapper.toDTO(updated);
    }

    @Transactional
    public void softDelete(Long id) {
        categoryRepository.softDelete(id);
    }

}
