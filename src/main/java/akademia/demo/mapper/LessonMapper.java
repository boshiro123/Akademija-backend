package akademia.demo.mapper;

import akademia.demo.dto.LessonDTO;
import akademia.demo.model.Child;
import akademia.demo.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LessonMapper {

  @Mapping(source = "teacher.id", target = "teacherId")
  @Mapping(source = "children", target = "childrenIds", qualifiedByName = "childrenToIds")
  LessonDTO toDto(Lesson lesson);

  @Mapping(target = "teacher", ignore = true)
  @Mapping(target = "children", ignore = true)
  Lesson toEntity(LessonDTO lessonDTO);

  @Named("childrenToIds")
  default List<Long> childrenToIds(List<Child> children) {
    if (children == null)
      return null;
    return children.stream()
        .map(Child::getId)
        .collect(Collectors.toList());
  }
}