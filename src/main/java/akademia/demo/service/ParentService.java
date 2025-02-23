package akademia.demo.service;

import akademia.demo.dto.ParentDTO;
import akademia.demo.dto.ChildDTO;
import akademia.demo.dto.LessonDTO;

import java.util.List;

public interface ParentService {
  ParentDTO registerParent(ParentDTO parentDTO);

  ParentDTO getParentById(Long id);

  List<ParentDTO> getAllParents();

  ParentDTO updateParent(Long id, ParentDTO parentDTO);

  void deleteParent(Long id);

  // Методы для работы с детьми
  ChildDTO addChild(Long parentId, ChildDTO childDTO);

  List<ChildDTO> getParentChildren(Long parentId);

  // Получение расписания занятий для всех детей родителя
  List<LessonDTO> getChildrenLessons(Long parentId);
}