package akademia.demo.service;

import akademia.demo.dto.ChildDTO;
import akademia.demo.dto.LessonDTO;
import java.util.List;

public interface ChildService {
  ChildDTO createChild(ChildDTO childDTO);

  ChildDTO getChildById(Long id);

  List<ChildDTO> getAllChildren();

  ChildDTO updateChild(Long id, ChildDTO childDTO);

  void deleteChild(Long id);

  // Методы для работы с занятиями ребенка
  List<LessonDTO> getChildLessons(Long childId);

  List<LessonDTO> getChildUpcomingLessons(Long childId);

  boolean isChildAvailableForLesson(Long childId, Long lessonId);
}