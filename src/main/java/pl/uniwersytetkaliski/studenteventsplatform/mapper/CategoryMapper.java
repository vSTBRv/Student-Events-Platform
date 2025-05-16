package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CategoryResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.CreateCategoryDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categoryDTO.UpdateCategoryDTO;
import pl.uniwersytetkaliski.studenteventsplatform.model.Category;

@Component
public class CategoryMapper implements Mapper<Category,CategoryResponseDTO,CreateCategoryDTO, UpdateCategoryDTO> {

    @Override
    public Category toEntity(CreateCategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    @Override
    public Category updateEntity(Category entity, UpdateCategoryDTO dto) {
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
