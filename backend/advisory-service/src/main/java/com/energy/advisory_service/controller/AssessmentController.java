package com.energy.advisory_service.controller;

import com.energy.advisory_service.dto.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.dto.response.AssessmentResponse;
import com.energy.advisory_service.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public AssessmentResponse submitAssessment(@Valid @RequestBody SubmitEnergyAssessmentRequest request) {
        return assessmentService.submitAssessment(request);
    }

    @GetMapping("/{assessmentId}")
    public AssessmentResponse getAssessment(@PathVariable String assessmentId) {
        return assessmentService.getAssessmentByAssessmentId(assessmentId);
    }
}