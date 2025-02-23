package akademia.demo.service.impl;

import akademia.demo.dto.LessonDTO;
import akademia.demo.dto.ParentDTO;
import akademia.demo.dto.ChildDTO;
import akademia.demo.mapper.LessonMapper;
import akademia.demo.model.Parent;
import akademia.demo.model.Child;
import akademia.demo.repository.LessonRepository;
import akademia.demo.repository.ParentRepository;
import akademia.demo.repository.ChildRepository;
import akademia.demo.mapper.ParentMapper;
import akademia.demo.mapper.ChildMapper;
import akademia.demo.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {
  private final ParentRepository parentRepository;
  private final ChildRepository childRepository;
  private final ParentMapper parentMapper;
  private final ChildMapper childMapper;
  private final LessonMapper lessonMapper;
  private final LessonRepository lessonRepository;

  @Override
  @Transactional
  public ParentDTO registerParent(ParentDTO parentDTO) {
    if (parentRepository.existsByEmail(parentDTO.getEmail())) {
      throw new RuntimeException("Email already exists");
    }
    Parent parent = parentMapper.toEntity(parentDTO);
    return parentMapper.toDto(parentRepository.save(parent));
  }

  @Override
  public ParentDTO getParentById(Long id) {
    Parent parent = parentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Parent not found"));
    return parentMapper.toDto(parent);
  }

  @Override
  public List<ParentDTO> getAllParents() {
    return parentRepository.findAll().stream()
        .map(parentMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ChildDTO addChild(Long parentId, ChildDTO childDTO) {
    Parent parent = parentRepository.findById(parentId)
        .orElseThrow(() -> new RuntimeException("Parent not found"));

    Child child = childMapper.toEntity(childDTO);
    child.setParent(parent);
    Child savedChild = childRepository.save(child);

    return childMapper.toDto(savedChild);
  }

  @Override
  @Transactional
  public ParentDTO updateParent(Long id, ParentDTO parentDTO) {
    Parent existingParent = parentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Parent not found"));

    // Проверка email только если он изменился
    if (!existingParent.getEmail().equals(parentDTO.getEmail())
        && parentRepository.existsByEmail(parentDTO.getEmail())) {
      throw new RuntimeException("Email already exists");
    }

    existingParent.setFirstName(parentDTO.getFirstName());
    existingParent.setLastName(parentDTO.getLastName());
    existingParent.setEmail(parentDTO.getEmail());

    return parentMapper.toDto(parentRepository.save(existingParent));
  }

  @Override
  @Transactional
  public void deleteParent(Long id) {
    Parent parent = parentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Parent not found"));

    // Дети будут удалены автоматически благодаря cascade = CascadeType.ALL и
    // orphanRemoval = true
    parentRepository.delete(parent);
  }

  @Override
  public List<ChildDTO> getParentChildren(Long parentId) {
    Parent parent = parentRepository.findById(parentId)
        .orElseThrow(() -> new RuntimeException("Parent not found"));

    return parent.getChildren().stream()
        .map(childMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<LessonDTO> getChildrenLessons(Long parentId) {
    Parent parent = parentRepository.findById(parentId)
        .orElseThrow(() -> new RuntimeException("Parent not found"));

    return parent.getChildren().stream()
        .flatMap(child -> lessonRepository.findByChildId(child.getId()).stream())
        .distinct() // Убираем дубликаты, если они есть
        .map(lessonMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void removeChild(Long parentId, Long childId) {
    Parent parent = parentRepository.findById(parentId)
        .orElseThrow(() -> new RuntimeException("Parent not found"));

    Child child = childRepository.findById(childId)
        .orElseThrow(() -> new RuntimeException("Child not found"));

    parent.getChildren().remove(child);
    parentRepository.save(parent);
  }

  // Остальные методы реализации
}