# GW2 Project — Updated Backend Scope

## Backend team focus

Team members assigned to backend:
- Shailesh
- Mrudulaa
- Honey

## Implemented backend responsibilities

### 1. REST controllers

Implemented controllers:
- `AssessmentController`
- `ReportController`

Implemented endpoint pairs:
- `POST /submitEnergyAssessment` and alias `POST /assessments`
- `GET /assessments/{assessmentId}`
- `POST /generateSustainabilityReport` and alias `POST /reports`
- `GET /reports/assessment/{assessmentId}`

### 2. Service layer

Implemented orchestration services:
- `AssessmentService`
- `ReportService`

Responsibilities:
- validate lifecycle rules
- call adapter services
- derive `Completed`, `Partial`, and `Failed` states
- generate report summary metrics and recommendations

### 3. Adapter layer

Implemented adapters:
- `EnergyServiceAdapter`
- `WeatherServiceAdapter`
- `CarbonServiceAdapter`

Responsibilities:
- preserve a service boundary for external integrations
- provide deterministic mock behaviour for testing
- simulate missing data for partial and failed flows

### 4. Data models

Implemented GW1-aligned DTOs:
- `SubmitEnergyAssessmentRequest`
- `SubmitEnergyAssessmentResponse`
- `GenerateSustainabilityReportRequest`
- `GenerateSustainabilityReportResponse`
- shared nested models for `property` and `timeWindow`

### 5. Validation and error handling

Implemented:
- nested request validation
- date-time and chronology checks
- structured API error responses
- status conflict checks during report generation

### 6. Documentation and testing artefacts

Produced:
- `openapi/openapi.yaml`
- `testing/postman_collection.json`
- `testing/test_cases.md`
- REST-specific sequence diagrams in `diagrams/`
- technical notes in `documentation/`
