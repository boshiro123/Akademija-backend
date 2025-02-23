package akademia.demo.service.impl;

import akademia.demo.dto.ChildDTO;
import akademia.demo.dto.LessonDTO;
import akademia.demo.model.Child;
import akademia.demo.model.Lesson;
import akademia.demo.repository.ChildRepository;
import akademia.demo.repository.LessonRepository;
import akademia.demo.mapper.ChildMapper;
import akademia.demo.mapper.LessonMapper;
import akademia.demo.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {
  private final ChildRepository childRepository;
  private final LessonRepository lessonRepository;
  private final ChildMapper childMapper;
  private final LessonMapper lessonMapper;

  @Override
  @Transactional
  public ChildDTO createChild(ChildDTO childDTO) {
    Child child = childMapper.toEntity(childDTO);
    return childMapper.toDto(childRepository.save(child));
  }

  @Override
  public ChildDTO getChildById(Long id) {
    return childRepository.findById(id)
        .map(childMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Child not found"));
  }

  @Override
  public List<ChildDTO> getAllChildren() {
    return childRepository.findAll().stream()
        .map(childMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ChildDTO updateChild(Long id, ChildDTO childDTO) {
    Child existingChild = childRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Child not found"));

    existingChild.setFirstName(childDTO.getFirstName());
    existingChild.setLastName(childDTO.getLastName());

    return childMapper.toDto(childRepository.save(existingChild));
  }

  @Override
  @Transactional
  public void deleteChild(Long id) {
    Child child = childRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Child not found"));

    // Получаем все уроки, в которых участвует ребенок, и очищаем связи
    lessonRepository.findByChildId(child.getId()).forEach(lesson -> lesson.getChildren().remove(child));
    childRepository.delete(child);
  }

  @Override
  public List<LessonDTO> getChildLessons(Long childId) {
    return lessonRepository.findByChildId(childId).stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<LessonDTO> getChildUpcomingLessons(Long childId) {
    LocalDateTime now = LocalDateTime.now();
    return lessonRepository.findByChildId(childId).stream()
        .filter(lesson -> lesson.getStartTime().isAfter(now))
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isChildAvailableForLesson(Long childId, Long lessonId) {
    Child child = childRepository.findById(childId)
        .orElseThrow(() -> new RuntimeException("Child not found"));

    Lesson targetLesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    // Проверяем, нет ли пересечений с другими уроками
    return lessonRepository.findByChildId(child.getId()).stream()
        .noneMatch(lesson -> {
          LocalDateTime lessonStart = lesson.getStartTime();
          LocalDateTime lessonEnd = lessonStart.plusMinutes(lesson.getDurationMinutes());
          LocalDateTime targetStart = targetLesson.getStartTime();
          LocalDateTime targetEnd = targetStart.plusMinutes(targetLesson.getDurationMinutes());

          return !(lessonEnd.isBefore(targetStart) || lessonStart.isAfter(targetEnd));
        });
  }
}