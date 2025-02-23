package akademia.demo.service;

import akademia.demo.dto.TeacherDTO;
import akademia.demo.dto.LessonDTO;
import java.util.List;
import java.time.LocalDateTime;

public interface TeacherService {
  TeacherDTO registerTeacher(TeacherDTO teacherDTO);

  TeacherDTO getTeacherById(Long id);

  List<TeacherDTO> getAllTeachers();

  TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

  void deleteTeacher(Long id);

  // Методы для работы с занятиями
  List<LessonDTO> getTeacherLessons(Long teacherId);

  List<LessonDTO> getTeacherLessonsByDateRange(Long teacherId, LocalDateTime start, LocalDateTime end);

  LessonDTO createLesson(Long teacherId, LessonDTO lessonDTO);

  LessonDTO updateLesson(Long teacherId, Long lessonId, LessonDTO lessonDTO);

  void deleteLesson(Long teacherId, Long lessonId);
}