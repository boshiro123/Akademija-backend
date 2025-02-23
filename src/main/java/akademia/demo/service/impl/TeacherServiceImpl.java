package akademia.demo.service.impl;

import akademia.demo.dto.TeacherDTO;
import akademia.demo.dto.LessonDTO;
import akademia.demo.model.Teacher;
import akademia.demo.model.Lesson;
import akademia.demo.repository.TeacherRepository;
import akademia.demo.repository.LessonRepository;
import akademia.demo.mapper.TeacherMapper;
import akademia.demo.mapper.LessonMapper;
import akademia.demo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
  private final TeacherRepository teacherRepository;
  private final LessonRepository lessonRepository;
  private final TeacherMapper teacherMapper;
  private final LessonMapper lessonMapper;

  @Override
  @Transactional
  public TeacherDTO registerTeacher(TeacherDTO teacherDTO) {
    if (teacherRepository.existsByEmail(teacherDTO.getEmail())) {
      throw new RuntimeException("Email already exists");
    }
    Teacher teacher = teacherMapper.toEntity(teacherDTO);
    return teacherMapper.toDto(teacherRepository.save(teacher));
  }

  @Override
  public List<LessonDTO> getTeacherLessons(Long teacherId) {
    return lessonRepository.findByTeacherId(teacherId).stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<LessonDTO> getTeacherLessonsByDateRange(Long teacherId, LocalDateTime start, LocalDateTime end) {
    return lessonRepository.findByTeacherIdAndStartTimeBetween(teacherId, start, end).stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public LessonDTO createLesson(Long teacherId, LessonDTO lessonDTO) {
    Teacher teacher = teacherRepository.findById(teacherId)
        .orElseThrow(() -> new RuntimeException("Teacher not found"));

    Lesson lesson = lessonMapper.toEntity(lessonDTO);
    lesson.setTeacher(teacher);

    return lessonMapper.toDto(lessonRepository.save(lesson));
  }

  @Override
  public TeacherDTO getTeacherById(Long id) {
    Teacher teacher = teacherRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Teacher not found"));
    return teacherMapper.toDto(teacher);
  }

  @Override
  public List<TeacherDTO> getAllTeachers() {
    return teacherRepository.findAll().stream()
        .map(teacherMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
    Teacher existingTeacher = teacherRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Teacher not found"));

    // Проверка email только если он изменился
    if (!existingTeacher.getEmail().equals(teacherDTO.getEmail())
        && teacherRepository.existsByEmail(teacherDTO.getEmail())) {
      throw new RuntimeException("Email already exists");
    }

    existingTeacher.setFirstName(teacherDTO.getFirstName());
    existingTeacher.setLastName(teacherDTO.getLastName());
    existingTeacher.setEmail(teacherDTO.getEmail());

    return teacherMapper.toDto(teacherRepository.save(existingTeacher));
  }

  @Override
  @Transactional
  public void deleteTeacher(Long id) {
    Teacher teacher = teacherRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Teacher not found"));

    // Удаляем учителя вместе со всеми его уроками (благодаря cascade =
    // CascadeType.ALL)
    teacherRepository.delete(teacher);
  }

  @Override
  @Transactional
  public LessonDTO updateLesson(Long teacherId, Long lessonId, LessonDTO lessonDTO) {
    teacherRepository.findById(teacherId)
        .orElseThrow(() -> new RuntimeException("Teacher not found"));

    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    // Проверяем, принадлежит ли урок этому учителю
    if (!lesson.getTeacher().getId().equals(teacherId)) {
      throw new RuntimeException("This lesson doesn't belong to the specified teacher");
    }

    // Проверяем доступность временного слота
    if (!isTimeSlotAvailable(lessonDTO.getStartTime(), lessonDTO.getDurationMinutes(), teacherId)) {
      throw new RuntimeException("Time slot is not available");
    }

    lesson.setSubject(lessonDTO.getSubject());
    lesson.setStartTime(lessonDTO.getStartTime());
    lesson.setDurationMinutes(lessonDTO.getDurationMinutes());

    return lessonMapper.toDto(lessonRepository.save(lesson));
  }

  @Override
  @Transactional
  public void deleteLesson(Long teacherId, Long lessonId) {
    teacherRepository.findById(teacherId)
        .orElseThrow(() -> new RuntimeException("Teacher not found"));

    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    // Проверяем, принадлежит ли урок этому учителю
    if (!lesson.getTeacher().getId().equals(teacherId)) {
      throw new RuntimeException("This lesson doesn't belong to the specified teacher");
    }

    // Очищаем связи с детьми перед удалением
    lesson.getChildren().clear();
    lessonRepository.delete(lesson);
  }

  private boolean isTimeSlotAvailable(LocalDateTime startTime, int durationMinutes, Long teacherId) {
    LocalDateTime endTime = startTime.plusMinutes(durationMinutes);
    List<Lesson> overlappingLessons = lessonRepository.findOverlappingLessons(
        teacherId,
        startTime,
        endTime);
    return overlappingLessons.isEmpty();
  }

  // Остальные методы реализации
}