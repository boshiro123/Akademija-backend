package akademia.demo.service.impl;

import akademia.demo.dto.*;
import akademia.demo.model.*;
import akademia.demo.repository.*;
import akademia.demo.mapper.*;
import akademia.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
  private final ParentRepository parentRepository;
  private final TeacherRepository teacherRepository;
  private final LessonRepository lessonRepository;
  private final ChildRepository childRepository;
  private final ParentMapper parentMapper;
  private final TeacherMapper teacherMapper;
  private final LessonMapper lessonMapper;
  private final ChildMapper childMapper;

  @Override
  @Transactional
  public ParentDTO createParent(ParentDTO parentDTO) {
    if (parentRepository.existsByEmail(parentDTO.getEmail())) {
      throw new RuntimeException("Email already exists");
    }

    Parent parent = parentMapper.toEntity(parentDTO);
    parent.setPassword(parentDTO.getPassword());

    // Обработка списка детей, если они есть
    if (parentDTO.getChildren() != null && !parentDTO.getChildren().isEmpty()) {
      List<Child> children = parentDTO.getChildren().stream()
          .map(childDTO -> {
            Child child = childMapper.toEntity(childDTO);
            child.setParent(parent);
            return child;
          })
          .collect(Collectors.toList());
      parent.setChildren(children);
    }

    return parentMapper.toDto(parentRepository.save(parent));
  }

  @Override
  @Transactional
  public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
    if (teacherRepository.existsByEmail(teacherDTO.getEmail())) {
      throw new RuntimeException("Email already exists");
    }
    Teacher teacher = teacherMapper.toEntity(teacherDTO);
    teacher.setPassword(teacherDTO.getPassword());
    return teacherMapper.toDto(teacherRepository.save(teacher));
  }

  @Override
  @Transactional
  public LessonDTO assignChildrenToLesson(Long lessonId, List<Long> childrenIds) {
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    List<Child> children = childRepository.findAllById(childrenIds);
    lesson.getChildren().addAll(children);

    return lessonMapper.toDto(lessonRepository.save(lesson));
  }

  @Override
  @Transactional
  public LessonDTO removeChildrenFromLesson(Long lessonId, List<Long> childrenIds) {
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    List<Child> childrenToRemove = childRepository.findAllById(childrenIds);
    lesson.getChildren().removeAll(childrenToRemove);

    return lessonMapper.toDto(lessonRepository.save(lesson));
  }

  @Override
  public List<LessonDTO> getAllLessons() {
    return lessonRepository.findAll().stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<LessonDTO> getLessonsByDateRange(LocalDateTime start, LocalDateTime end) {
    System.out.println("start: " + start);
    System.out.println("end: " + end);
    return lessonRepository.findLessonsByStartTimeBetween(start, end).stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public int getTotalStudentsCount() {
    return (int) childRepository.count();
  }

  @Override
  public int getTotalTeachersCount() {
    return (int) teacherRepository.count();
  }

  @Override
  public int getTotalLessonsCount() {
    return (int) lessonRepository.count();
  }

  @Override
  public List<LessonDTO> getUpcomingLessons() {
    LocalDateTime now = LocalDateTime.now();
    return lessonRepository.findLessonsByStartTimeBetween(
        now, now.plusDays(7)).stream()
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }
}