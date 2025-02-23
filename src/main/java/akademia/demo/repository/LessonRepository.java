package akademia.demo.repository;

import akademia.demo.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTeacherId(Long teacherId);

    List<Lesson> findLessonsByStartTimeBetween(
            LocalDateTime start,
            LocalDateTime end);

    List<Lesson> findByTeacherIdAndStartTimeBetween(
            Long teacherId,
            LocalDateTime start,
            LocalDateTime end);

    @Query("SELECT l FROM Lesson l WHERE l.teacher.id = :teacherId " +
            "AND ((l.startTime BETWEEN :start AND :end) " +
            "OR (l.startTime <= :start AND DATEADD(MINUTE, l.durationMinutes, l.startTime) >= :start))")
    List<Lesson> findOverlappingLessons(
            Long teacherId,
            LocalDateTime start,
            LocalDateTime enzd);

    @Query("SELECT l FROM Lesson l JOIN l.children c WHERE c.id = :childId")
    List<Lesson> findByChildId(Long childId);
}