package akademia.demo.controller;

import akademia.demo.dto.LessonDTO;
import akademia.demo.dto.ChildDTO;
import akademia.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
  private final LessonService lessonService;

  @PostMapping
  public ResponseEntity<LessonDTO> createLesson(@RequestBody LessonDTO lessonDTO) {
    return ResponseEntity.ok(lessonService.createLesson(lessonDTO));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
    return ResponseEntity.ok(lessonService.getLessonById(id));
  }

  @GetMapping
  public ResponseEntity<List<LessonDTO>> getAllLessons() {
    return ResponseEntity.ok(lessonService.getAllLessons());
  }

  @PutMapping("/{id}")
  public ResponseEntity<LessonDTO> updateLesson(
      @PathVariable Long id,
      @RequestBody LessonDTO lessonDTO) {
    return ResponseEntity.ok(lessonService.updateLesson(id, lessonDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
    lessonService.deleteLesson(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{lessonId}/participants")
  public ResponseEntity<List<ChildDTO>> getLessonParticipants(@PathVariable Long lessonId) {
    return ResponseEntity.ok(lessonService.getLessonParticipants(lessonId));
  }

  @PostMapping("/{lessonId}/participants")
  public ResponseEntity<LessonDTO> addParticipants(
      @PathVariable Long lessonId,
      @RequestBody List<Long> childrenIds) {
    return ResponseEntity.ok(lessonService.addParticipants(lessonId, childrenIds));
  }

  @DeleteMapping("/{lessonId}/participants")
  public ResponseEntity<LessonDTO> removeParticipants(
      @PathVariable Long lessonId,
      @RequestBody List<Long> childrenIds) {
    return ResponseEntity.ok(lessonService.removeParticipants(lessonId, childrenIds));
  }

  @GetMapping("/by-date-range")
  public ResponseEntity<List<LessonDTO>> getLessonsByDateRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
    return ResponseEntity.ok(lessonService.getLessonsByDateRange(start, end));
  }

  @GetMapping("/check-availability")
  public ResponseEntity<Boolean> isTimeSlotAvailable(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
      @RequestParam int durationMinutes,
      @RequestParam Long teacherId) {
    return ResponseEntity.ok(lessonService.isTimeSlotAvailable(startTime, durationMinutes, teacherId));
  }
}