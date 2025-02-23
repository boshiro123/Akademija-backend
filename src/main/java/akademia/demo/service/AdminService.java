package akademia.demo.service;

import akademia.demo.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {
  // Управление пользователями
  ParentDTO createParent(ParentDTO parentDTO);

  TeacherDTO createTeacher(TeacherDTO teacherDTO);

  // Управление занятиями
  LessonDTO assignChildrenToLesson(Long lessonId, List<Long> childrenIds);

  LessonDTO removeChildrenFromLesson(Long lessonId, List<Long> childrenIds);

  // Управление расписанием
  List<LessonDTO> getAllLessons();

  List<LessonDTO> getLessonsByDateRange(LocalDateTime start, LocalDateTime end);

  // Статистика и отчеты
  int getTotalStudentsCount();

  int getTotalTeachersCount();

  int getTotalLessonsCount();

  List<LessonDTO> getUpcomingLessons();
}
