package akademia.demo.controller;

import akademia.demo.dto.*;
import akademia.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
  private final AdminService adminService;

  // Управление пользователями
  @PostMapping("/parents")
  public ResponseEntity<ParentDTO> createParent(@RequestBody ParentDTO parentDTO) {
    return ResponseEntity.ok(adminService.createParent(parentDTO));
  }

  @PostMapping("/teachers")
  public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
    return ResponseEntity.ok(adminService.createTeacher(teacherDTO));
  }

  // Управление занятиями
  @PostMapping("/lessons/{lessonId}/children")
  public ResponseEntity<LessonDTO> assignChildrenToLesson(
      @PathVariable Long lessonId,
      @RequestBody List<Long> childrenIds) {
    return ResponseEntity.ok(adminService.assignChildrenToLesson(lessonId, childrenIds));
  }

  @DeleteMapping("/lessons/{lessonId}/children")
  public ResponseEntity<LessonDTO> removeChildrenFromLesson(
      @PathVariable Long lessonId,
      @RequestBody List<Long> childrenIds) {
    return ResponseEntity.ok(adminService.removeChildrenFromLesson(lessonId, childrenIds));
  }

  // Управление расписанием
  @GetMapping("/lessons")
  public ResponseEntity<List<LessonDTO>> getAllLessons() {
    return ResponseEntity.ok(adminService.getAllLessons());
  }

  @GetMapping("/lessons/by-date-range")
  public ResponseEntity<List<LessonDTO>> getLessonsByDateRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
    return ResponseEntity.ok(adminService.getLessonsByDateRange(start, end));
  }

  // Статистика и отчеты
  @GetMapping("/statistics/students-count")
  public ResponseEntity<Integer> getTotalStudentsCount() {
    return ResponseEntity.ok(adminService.getTotalStudentsCount());
  }

  @GetMapping("/statistics/teachers-count")
  public ResponseEntity<Integer> getTotalTeachersCount() {
    return ResponseEntity.ok(adminService.getTotalTeachersCount());
  }

  @GetMapping("/statistics/lessons-count")
  public ResponseEntity<Integer> getTotalLessonsCount() {
    return ResponseEntity.ok(adminService.getTotalLessonsCount());
  }

  @GetMapping("/lessons/upcoming")
  public ResponseEntity<List<LessonDTO>> getUpcomingLessons() {
    return ResponseEntity.ok(adminService.getUpcomingLessons());
  }
}