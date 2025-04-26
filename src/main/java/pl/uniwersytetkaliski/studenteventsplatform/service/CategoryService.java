package pl.uniwersytetkaliski.studenteventsplatform.service;

import org.springframework.stereotype.Service;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CategoryDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;
import pl.uniwersytetkaliski.studenteventsplatform.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO mapToDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }
}
