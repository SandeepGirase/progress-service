package com.medimesh.progress.dto;

public record AnalysisResponse(
        int participantsStarted,
        int participantsPassed,
        int participantsFailed,
        double passRate
) {}

