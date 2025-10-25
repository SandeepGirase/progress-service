package com.medimesh.progress.controller;

import com.medimesh.progress.dto.CourseProgressEventRequest;
import com.medimesh.progress.dto.AnalysisResponse;
import com.medimesh.progress.model.CourseProgressEvent;
import com.medimesh.progress.model.EventType;
import com.medimesh.progress.service.EventService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/v1")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@Valid @RequestBody CourseProgressEventRequest req) {
        // parse timestamp
        OffsetDateTime ts;
        try {
            ts = OffsetDateTime.parse(req.timestamp());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Invalid timestamp, must be ISO 8601");
        }

        CourseProgressEvent event = new CourseProgressEvent(
                req.userId(),
                req.courseId(),
                ts,
                req.eventType()
        );

        CourseProgressEvent saved = service.saveEvent(event);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/events/user/{userId}")
    public ResponseEntity<?> getEventsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(service.getEventsForUser(userId));
    }

    @GetMapping("/analysis/course/{courseId}")
    public ResponseEntity<AnalysisResponse> analyzeCourse(@PathVariable String courseId) {
        AnalysisResponse resp = service.analyzeCourse(courseId);
        return ResponseEntity.ok(resp);
    }
}

