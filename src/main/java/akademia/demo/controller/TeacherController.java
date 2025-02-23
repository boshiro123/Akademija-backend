package akademia.demo.controller;

import akademia.demo.dto.TeacherDTO;
import akademia.demo.dto.LessonDTO;
import akademia.demo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
  private final TeacherService teacherService;

  @PostMapping("/register")
  public ResponseEntity<TeacherDTO> registerTeacher(@RequestBody TeacherDTO teacherDTO) {
    return ResponseEntity.ok(teacherService.registerTeacher(teacherDTO));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
    return ResponseEntity.ok(teacherService.getTeacherById(id));
  }

  @GetMapping
  public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
    return ResponseEntity.ok(teacherService.getAllTeachers());
  }

  @PutMapping("/{id}")
  public ResponseEntity<TeacherDTO> updateTeacher(
      @PathVariable Long id,
      @RequestBody TeacherDTO teacherDTO) {
    return ResponseEntity.ok(teacherService.updateTeacher(id, teacherDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
    teacherService.deleteTeacher(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{teacherId}/lessons")
  public ResponseEntity<List<LessonDTO>> getTeacherLessons(@PathVariable Long teacherId) {
    return ResponseEntity.ok(teacherService.getTeacherLessons(teacherId));
  }

  @GetMapping("/{teacherId}/lessons/by-date-range")
  public ResponseEntity<List<LessonDTO>> getTeacherLessonsByDateRange(
      @PathVariable Long teacherId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
    return ResponseEntity.ok(teacherService.getTeacherLessonsByDateRange(teacherId, start, end));
  }

  @PostMapping("/{teacherId}/lessons")
  public ResponseEntity<LessonDTO> createLesson(
      @PathVariable Long teacherId,
      @RequestBody LessonDTO lessonDTO) {
    return ResponseEntity.ok(teacherService.createLesson(teacherId, lessonDTO));
  }

  @PutMapping("/{teacherId}/lessons/{lessonId}")
  public ResponseEntity<LessonDTO> updateLesson(
      @PathVariable Long teacherId,
      @PathVariable Long lessonId,
      @RequestBody LessonDTO lessonDTO) {
    return ResponseEntity.ok(teacherService.updateLesson(teacherId, lessonId, lessonDTO));
  }

  @DeleteMapping("/{teacherId}/lessons/{lessonId}")
  public ResponseEntity<Void> deleteLesson(
      @PathVariable Long teacherId,
      @PathVariable Long lessonId) {
    teacherService.deleteLesson(teacherId, lessonId);
    return ResponseEntity.noContent().build();
  }
}