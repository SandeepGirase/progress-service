package com.medimesh.progress.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "course_progress_event")
@Data
@AllArgsConstructor
public class CourseProgressEvent {
    @Id
    @Column(name = "event_id", nullable = false, updatable = false)
    private String eventId; // store as String for simplicity

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String courseId;

    @Column(nullable = false)
    private OffsetDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    public CourseProgressEvent() { }

    public CourseProgressEvent(String userId, String courseId, OffsetDateTime timestamp, EventType eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.userId = userId;
        this.courseId = courseId;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    // Getters and setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}

