package akademia.demo.mapper;

import akademia.demo.dto.ParentDTO;
import akademia.demo.model.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ChildMapper.class })
public interface ParentMapper {

  ParentDTO toDto(Parent parent);

  @Mapping(target = "children", ignore = true)
  Parent toEntity(ParentDTO parentDTO);
}