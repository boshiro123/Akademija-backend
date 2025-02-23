package akademia.demo.mapper;

import akademia.demo.dto.ChildDTO;
import akademia.demo.model.Child;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChildMapper {

  @Mapping(source = "parent.id", target = "parentId")
  ChildDTO toDto(Child child);

  @Mapping(target = "parent", ignore = true)
  Child toEntity(ChildDTO childDTO);
}