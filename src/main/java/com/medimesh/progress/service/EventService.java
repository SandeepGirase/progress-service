package com.medimesh.progress.service;

import com.medimesh.progress.model.CourseProgressEvent;
import com.medimesh.progress.model.EventType;
import com.medimesh.progress.repo.CourseProgressEventRepository;
import com.medimesh.progress.dto.AnalysisResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final CourseProgressEventRepository repo;

    public EventService(CourseProgressEventRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public CourseProgressEvent saveEvent(CourseProgressEvent event) {
        return repo.save(event);
    }

    public List<CourseProgressEvent> getEventsForUser(String userId) {
        return repo.findByUserIdOrderByTimestampAsc(userId);
    }

    /**
     * Core analysis logic. We return unique user counts for started/passed/failed.
     */
    public AnalysisResponse analyzeCourse(String courseId) {
        List<CourseProgressEvent> events = repo.findByCourseId(courseId);
        // Use sets to count unique users per event type.
        Set<String> started = new HashSet<>();
        Set<String> passed = new HashSet<>();
        Set<String> failed = new HashSet<>();

        for (CourseProgressEvent e : events) {
            switch (e.getEventType()) {
                case COURSE_STARTED -> started.add(e.getUserId());
                case COURSE_PASSED -> passed.add(e.getUserId());
                case COURSE_FAILED -> failed.add(e.getUserId());
            }
        }

        int participantsStarted = started.size();
        int participantsPassed = passed.size();
        int participantsFailed = failed.size();
        double passRate = 0.0;
        int denom = participantsPassed + participantsFailed;
        if (denom > 0) {
            passRate = (participantsPassed * 100.0) / denom;
        }

        return new AnalysisResponse(participantsStarted, participantsPassed, participantsFailed, passRate);
    }
}

