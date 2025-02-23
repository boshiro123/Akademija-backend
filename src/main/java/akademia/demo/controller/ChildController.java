package akademia.demo.controller;

import akademia.demo.dto.ChildDTO;
import akademia.demo.dto.LessonDTO;
import akademia.demo.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children")
@RequiredArgsConstructor
public class ChildController {
  private final ChildService childService;

  @PostMapping
  public ResponseEntity<ChildDTO> createChild(@RequestBody ChildDTO childDTO) {
    return ResponseEntity.ok(childService.createChild(childDTO));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ChildDTO> getChildById(@PathVariable Long id) {
    return ResponseEntity.ok(childService.getChildById(id));
  }

  @GetMapping
  public ResponseEntity<List<ChildDTO>> getAllChildren() {
    return ResponseEntity.ok(childService.getAllChildren());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ChildDTO> updateChild(
      @PathVariable Long id,
      @RequestBody ChildDTO childDTO) {
    return ResponseEntity.ok(childService.updateChild(id, childDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
    childService.deleteChild(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{childId}/lessons")
  public ResponseEntity<List<LessonDTO>> getChildLessons(@PathVariable Long childId) {
    return ResponseEntity.ok(childService.getChildLessons(childId));
  }

  @GetMapping("/{childId}/lessons/upcoming")
  public ResponseEntity<List<LessonDTO>> getChildUpcomingLessons(@PathVariable Long childId) {
    return ResponseEntity.ok(childService.getChildUpcomingLessons(childId));
  }

  @GetMapping("/{childId}/lessons/{lessonId}/availability")
  public ResponseEntity<Boolean> checkLessonAvailability(
      @PathVariable Long childId,
      @PathVariable Long lessonId) {
    return ResponseEntity.ok(childService.isChildAvailableForLesson(childId, lessonId));
  }
}