package akademia.demo.controller;

import akademia.demo.dto.ParentDTO;
import akademia.demo.dto.ChildDTO;
import akademia.demo.dto.LessonDTO;
import akademia.demo.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {
  private final ParentService parentService;

  @PostMapping("/register")
  public ResponseEntity<ParentDTO> registerParent(@RequestBody ParentDTO parentDTO) {
    return ResponseEntity.ok(parentService.registerParent(parentDTO));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ParentDTO> getParentById(@PathVariable Long id) {
    return ResponseEntity.ok(parentService.getParentById(id));
  }

  @GetMapping
  public ResponseEntity<List<ParentDTO>> getAllParents() {
    return ResponseEntity.ok(parentService.getAllParents());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ParentDTO> updateParent(
      @PathVariable Long id,
      @RequestBody ParentDTO parentDTO) {
    return ResponseEntity.ok(parentService.updateParent(id, parentDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
    parentService.deleteParent(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{parentId}/children")
  public ResponseEntity<List<ChildDTO>> getParentChildren(@PathVariable Long parentId) {
    return ResponseEntity.ok(parentService.getParentChildren(parentId));
  }

  @GetMapping("/{parentId}/children/lessons")
  public ResponseEntity<List<LessonDTO>> getChildrenLessons(@PathVariable Long parentId) {
    return ResponseEntity.ok(parentService.getChildrenLessons(parentId));
  }

  @PostMapping("/{parentId}/children")
  public ResponseEntity<ChildDTO> addChild(
      @PathVariable Long parentId,
      @RequestBody ChildDTO childDTO) {
    return ResponseEntity.ok(parentService.addChild(parentId, childDTO));
  }

  @DeleteMapping("/{parentId}/children/{childId}")
  public ResponseEntity<Void> removeChild(
      @PathVariable Long parentId,
      @PathVariable Long childId) {
    parentService.removeChild(parentId, childId);
    return ResponseEntity.noContent().build();
  }
}