package akademia.demo.mapper;

import akademia.demo.dto.TeacherDTO;
import akademia.demo.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { LessonMapper.class })
public interface TeacherMapper {

  TeacherDTO toDto(Teacher teacher);

  @Mapping(target = "lessons", ignore = true)
  Teacher toEntity(TeacherDTO teacherDTO);
}