package com.energy.advisory_service.controller;

<<<<<<< HEAD
import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.model.response.SubmitEnergyAssessmentResponse;
import com.energy.advisory_service.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energy.advisory_service.model.request.SubmitEnergyAssessmentRequest;
import com.energy.advisory_service.model.response.SubmitEnergyAssessmentResponse;
import com.energy.advisory_service.service.AssessmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
>>>>>>> 506482b (GW2 backend implementation and validation updation)

@RestController
@RequestMapping
@Tag(name = "Assessment API", description = "APIs for submitting and retrieving energy sustainability assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

<<<<<<< HEAD
    @PostMapping({"/submitEnergyAssessment", "/assessments"})
=======
    @PostMapping({"/submitEnergyAssessment"})
>>>>>>> 506482b (GW2 backend implementation and validation updation)
    @Operation(summary = "Submit an energy assessment request")
    public ResponseEntity<SubmitEnergyAssessmentResponse> submitAssessment(@Valid @RequestBody SubmitEnergyAssessmentRequest request) {
        SubmitEnergyAssessmentResponse response = assessmentService.submitAssessment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/assessments/{assessmentId}")
    @Operation(summary = "Get a previously created assessment by assessmentId")
    public ResponseEntity<SubmitEnergyAssessmentResponse> getAssessmentById(@PathVariable String assessmentId) {
        return ResponseEntity.ok(assessmentService.getAssessmentById(assessmentId));
    }
}
