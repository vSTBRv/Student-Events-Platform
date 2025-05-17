package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryUpdateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;

@Component
public class CategoryMapper implements Mapper<Category, CategoryResponseDTO, CategoryCreateDTO, CategoryUpdateDTO> {

    @Override
    public Category toEntity(CategoryCreateDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    @Override
    public Category updateEntity(Category entity, CategoryUpdateDTO dto) {
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public CategoryResponseDTO toResponseDTO(Category entity) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setName(entity.getName());
        categoryResponseDTO.setId(entity.getId());
        return categoryResponseDTO;
    }
}
