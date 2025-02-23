package akademia.demo.mapper;

import akademia.demo.dto.LessonDTO;
import akademia.demo.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ChildMapper.class)
public interface LessonMapper {

  @Mapping(source = "teacher.id", target = "teacherId")
  @Mapping(source = "children", target = "children")
  LessonDTO toDto(Lesson lesson);

  @Mapping(target = "teacher", ignore = true)
  @Mapping(target = "children", ignore = true)
  Lesson toEntity(LessonDTO lessonDTO);
}