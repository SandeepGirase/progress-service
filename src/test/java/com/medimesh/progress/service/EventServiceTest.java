package com.medimesh.progress.service;

import com.medimesh.progress.dto.AnalysisResponse;
import com.medimesh.progress.model.CourseProgressEvent;
import com.medimesh.progress.model.EventType;
import com.medimesh.progress.repo.CourseProgressEventRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EventServiceTest {
    @Test
    void analyzeCourse_countsAndPassRateCorrectly() {
        CourseProgressEventRepository repo = mock(CourseProgressEventRepository.class);
        EventService service = new EventService(repo);

        // simulate events: userA started+passed, userB started+failed, userC started only
        var e1 = new CourseProgressEvent("userA", "courseX", OffsetDateTime.now(), EventType.COURSE_STARTED);
        var e2 = new CourseProgressEvent("userA", "courseX", OffsetDateTime.now(), EventType.COURSE_PASSED);
        var e3 = new CourseProgressEvent("userB", "courseX", OffsetDateTime.now(), EventType.COURSE_STARTED);
        var e4 = new CourseProgressEvent("userB", "courseX", OffsetDateTime.now(), EventType.COURSE_FAILED);
        var e5 = new CourseProgressEvent("userC", "courseX", OffsetDateTime.now(), EventType.COURSE_STARTED);

        when(repo.findByCourseId("courseX")).thenReturn(List.of(e1,e2,e3,e4,e5));

        AnalysisResponse r = service.analyzeCourse("courseX");

        assertThat(r.participantsStarted()).isEqualTo(3);
        assertThat(r.participantsPassed()).isEqualTo(1);
        assertThat(r.participantsFailed()).isEqualTo(1);
        assertThat(r.passRate()).isEqualTo(50.0);
        verify(repo).findByCourseId("courseX");
    }

    @Test
    void analyzeCourse_handlesDivisionByZero() {
        CourseProgressEventRepository repo = mock(CourseProgressEventRepository.class);
        EventService service = new EventService(repo);

        when(repo.findByCourseId("empty")).thenReturn(List.of());
        var r = service.analyzeCourse("empty");
        assertThat(r.participantsPassed()).isEqualTo(0);
        assertThat(r.participantsFailed()).isEqualTo(0);
        assertThat(r.passRate()).isEqualTo(0.0); // handled gracefully
    }
}

