package com.energy.advisory_service.controller;

import com.energy.advisory_service.model.request.SubmitAssessmentRequest;
import com.energy.advisory_service.model.response.AssessmentResponse;
import com.energy.advisory_service.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Tag(name = "Assessment Controller", description = "APIs for energy assessment submission")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping("/assessments")
    @Operation(summary = "Submit energy assessment")
    public ResponseEntity<AssessmentResponse> submitAssessment(@RequestBody SubmitAssessmentRequest request) {
        AssessmentResponse response = assessmentService.submitAssessment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/assessments/{assessmentId}")
    @Operation(summary = "Get assessment by assessmentId")
    public ResponseEntity<AssessmentResponse> getAssessmentById(@PathVariable String assessmentId) {
        AssessmentResponse response = assessmentService.getAssessmentById(assessmentId);
        return ResponseEntity.ok(response);
    }
}