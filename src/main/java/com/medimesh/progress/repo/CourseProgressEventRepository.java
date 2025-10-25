package com.medimesh.progress.repo;

import com.medimesh.progress.model.CourseProgressEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseProgressEventRepository extends JpaRepository<CourseProgressEvent, String> {
    List<CourseProgressEvent> findByUserIdOrderByTimestampAsc(String userId);
    List<CourseProgressEvent> findByCourseId(String courseId);
}
