package akademia.demo.service;

import akademia.demo.dto.LessonDTO;
import akademia.demo.dto.ChildDTO;
import java.util.List;
import java.time.LocalDateTime;

public interface LessonService {
  LessonDTO createLesson(LessonDTO lessonDTO);

  LessonDTO getLessonById(Long id);

  List<LessonDTO> getAllLessons();

  LessonDTO updateLesson(Long id, LessonDTO lessonDTO);

  void deleteLesson(Long id);

  // Методы для работы с участниками занятия
  List<ChildDTO> getLessonParticipants(Long lessonId);

  LessonDTO addParticipants(Long lessonId, List<Long> childrenIds);

  LessonDTO removeParticipants(Long lessonId, List<Long> childrenIds);

  // Методы для работы с расписанием
  List<LessonDTO> getLessonsByDateRange(LocalDateTime start, LocalDateTime end);

  boolean isTimeSlotAvailable(LocalDateTime startTime, int durationMinutes, Long teacherId);
}