package pl.uniwersytetkaliski.studenteventsplatform.mapper;

import org.springframework.stereotype.Component;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categorydto.CategoryResponseDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categorydto.CategoryCreateDTO;
import pl.uniwersytetkaliski.studenteventsplatform.dto.categorydto.CategoryUpdateDTO;
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
        categoryResponseDTO.setDeleted(entity.isDeleted());
        return categoryResponseDTO;
    }
}
