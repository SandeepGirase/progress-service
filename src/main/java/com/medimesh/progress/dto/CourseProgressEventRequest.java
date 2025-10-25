package com.medimesh.progress.dto;

import com.medimesh.progress.model.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseProgressEventRequest(
        @NotBlank String userId,
        @NotBlank String courseId,
        @NotBlank String timestamp, // ISO 8601 string
        @NotNull EventType eventType
) {}
