package com.medimesh.progress.controller;

import com.medimesh.progress.dto.CourseProgressEventRequest;
import com.medimesh.progress.model.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void postEvent_and_getAnalysis() {
        String courseId = "intCourse1";

        CourseProgressEventRequest req = new CourseProgressEventRequest(
                "userInt1",
                courseId,
                OffsetDateTime.now().toString(),
                EventType.COURSE_STARTED
        );

        ResponseEntity<String> postResp = rest.postForEntity("/v1/events", req, String.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // create passed event for same user
        CourseProgressEventRequest passReq = new CourseProgressEventRequest(
                "userInt1",
                courseId,
                OffsetDateTime.now().plusSeconds(10).toString(),
                EventType.COURSE_PASSED
        );
        rest.postForEntity("/v1/events", passReq, String.class);

        ResponseEntity<String> analysisResp = rest.getForEntity("/v1/analysis/course/" + courseId, String.class);
        assertThat(analysisResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String body = analysisResp.getBody();
        assertThat(body).contains("\"participantsStarted\"");
        assertThat(body).contains("\"participantsPassed\"");
        assertThat(body).contains("\"passRate\"");
        // minimal correctness: passed should be 1
        assertThat(body).contains("\"participantsPassed\":1");
    }
}

