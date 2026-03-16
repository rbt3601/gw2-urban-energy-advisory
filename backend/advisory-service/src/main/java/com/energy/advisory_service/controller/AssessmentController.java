package com.energy.advisory_service.controller;

import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.model.response.AssessmentResponse;
import com.energy.advisory_service.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessments")
@Tag(name = "Assessment Controller", description = "APIs for energy assessment submission and retrieval")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    @Operation(summary = "Submit energy assessment")
    public ResponseEntity<AssessmentResponse> submitAssessment(@Valid @RequestBody SubmitEnergyAssessmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assessmentService.submitAssessment(request));
    }

    @GetMapping("/{assessmentId}")
    @Operation(summary = "Get assessment by assessmentId")
    public ResponseEntity<AssessmentResponse> getAssessmentByAssessmentId(@PathVariable String assessmentId) {
        return ResponseEntity.ok(assessmentService.getAssessmentByAssessmentId(assessmentId));
    }
}