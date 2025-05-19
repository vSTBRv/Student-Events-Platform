package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CategoryResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.CreateCategoryDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;

@Component
public class CategoryMapper {
    public Category toEntity(CreateCategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    public CategoryResponseDTO toDTO(Category entity) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setName(entity.getName());
        categoryResponseDTO.setId(entity.getId());
        return categoryResponseDTO;
    }
}
