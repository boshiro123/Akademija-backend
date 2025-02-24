package akademia.demo.service.impl;

import akademia.demo.dto.LessonDTO;
import akademia.demo.dto.ChildDTO;
import akademia.demo.model.Lesson;
import akademia.demo.model.Child;
import akademia.demo.model.Teacher;
import akademia.demo.repository.LessonRepository;
import akademia.demo.repository.ChildRepository;
import akademia.demo.repository.TeacherRepository;
import akademia.demo.mapper.LessonMapper;
import akademia.demo.mapper.ChildMapper;
import akademia.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {
  private final LessonRepository lessonRepository;
  private final ChildRepository childRepository;
  private final TeacherRepository teacherRepository;
  private final LessonMapper lessonMapper;
  private final ChildMapper childMapper;

  @Override
  @Transactional
  public LessonDTO addParticipants(Long lessonId, List<Long> childrenIds) {
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    List<Child> children = childRepository.findAllById(childrenIds);
    lesson.getChildren().addAll(children);

    return lessonMapper.toDto(lessonRepository.save(lesson));
  }

  @Override
  public boolean isTimeSlotAvailable(LocalDateTime startTime, int durationMinutes, Long teacherId) {
    LocalDateTime endTime = startTime.plusMinutes(durationMinutes);
    List<Lesson> overlappingLessons = lessonRepository.findByTeacherIdAndStartTimeBetween(
        teacherId,
        startTime.minusMinutes(durationMinutes),
        endTime);
    return overlappingLessons.isEmpty();
  }

  @Override
  @Transactional
  public LessonDTO createLesson(LessonDTO lessonDTO) {
    // Проверяем доступность временного слота
    if (!isTimeSlotAvailable(lessonDTO.getStartTime(),
        lessonDTO.getDurationMinutes(),
        lessonDTO.getTeacherId())) {
      throw new RuntimeException("Выбранное время недоступно для учителя");
    }

    // Преобразование DTO в сущность
    Lesson lesson = lessonMapper.toEntity(lessonDTO);

    // Получаем и устанавливаем учителя
    Teacher teacher = teacherRepository.findById(lessonDTO.getTeacherId())
        .orElseThrow(() -> new RuntimeException("Учитель не найден"));
    lesson.setTeacher(teacher);

    // Обрабатываем список детей, если он есть
    if (lessonDTO.getChildren() != null && !lessonDTO.getChildren().isEmpty()) {
      List<Long> childrenIds = lessonDTO.getChildren().stream()
          .map(ChildDTO::getId)
          .collect(Collectors.toList());
      List<Child> children = childRepository.findAllById(childrenIds);
      lesson.setChildren(children);
    }

    // Сохранение новой лекции
    Lesson savedLesson = lessonRepository.save(lesson);

    // Обратное преобразование в DTO и возврат результата
    return lessonMapper.toDto(savedLesson);
  }

  @Override
  @Transactional
  public LessonDTO removeParticipants(Long lessonId, List<Long> childrenIds) {
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    List<Child> childrenToRemove = childRepository.findAllById(childrenIds);
    lesson.getChildren().removeAll(childrenToRemove);

    return lessonMapper.toDto(lessonRepository.save(lesson));
  }

  @Override
  public List<LessonDTO> getLessonsByDateRange(LocalDateTime start, LocalDateTime end) {
    return lessonRepository.findLessonsByStartTimeBetween(start, end)
        .stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public LessonDTO getLessonById(Long id) {
    return lessonRepository.findById(id)
        .map(lessonMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));
  }

  @Override
  public List<LessonDTO> getAllLessons() {
    return lessonRepository.findAll()
        .stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public LessonDTO updateLesson(Long id, LessonDTO lessonDTO) {
    Lesson existingLesson = lessonRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    // Обновляем только изменяемые поля
    existingLesson.setSubject(lessonDTO.getSubject());
    existingLesson.setStartTime(lessonDTO.getStartTime());
    existingLesson.setDurationMinutes(lessonDTO.getDurationMinutes());

    // Если изменился учитель
    if (!existingLesson.getTeacher().getId().equals(lessonDTO.getTeacherId())) {
      Teacher newTeacher = teacherRepository.findById(lessonDTO.getTeacherId())
          .orElseThrow(() -> new RuntimeException("Teacher not found"));
      existingLesson.setTeacher(newTeacher);
    }

    return lessonMapper.toDto(lessonRepository.save(existingLesson));
  }

  @Override
  @Transactional
  public void deleteLesson(Long id) {
    Lesson lesson = lessonRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    // Очищаем связи с детьми перед удалением
    lesson.getChildren().clear();
    lessonRepository.delete(lesson);
  }

  @Override
  public List<ChildDTO> getLessonParticipants(Long lessonId) {
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    return lesson.getChildren().stream()
        .map(childMapper::toDto)
        .collect(Collectors.toList());
  }

  // Остальные методы реализации
}