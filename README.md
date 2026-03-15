# Urban Energy Usage & Sustainability Advisory Service — GW2

## Overview

This repository contains the Groupwork 2 implementation and testing package for the **Urban Energy Usage & Sustainability Advisory Service**. The backend is implemented in **Spring Boot** and aligns its main request and response contracts with the GW1 JSON mapping for the two selected backend operations:

- `submitEnergyAssessment`
- `generateSustainabilityReport`

The implementation keeps a service-oriented structure with controllers, orchestration services, adapter components, OpenAPI documentation, REST-specific sequence diagrams, and Postman test artefacts.

## Architecture

The application follows this flow:

Client → Controller → Service → Adapter → External / Simulated Services

### Layers

- **Controller**: exposes REST endpoints and validates incoming JSON payloads.
- **Service**: orchestrates data collection, status handling, and report generation.
- **Adapter**: simulates energy, weather, and carbon-intensity providers while preserving a replaceable external-service boundary.
- **Model**: request and response DTOs aligned with GW1.
- **Exception handling**: returns structured error payloads for validation and runtime failures.

## Main Endpoints

### Assessment

- `POST /submitEnergyAssessment`
- `POST /assessments` (alias for local testing convenience)
- `GET /assessments/{assessmentId}`

### Report

- `POST /generateSustainabilityReport`
- `POST /reports` (alias for local testing convenience)
- `GET /reports/assessment/{assessmentId}`

## Behaviour Summary

### submitEnergyAssessment

- Returns **Completed** when energy, weather, and carbon data are available.
- Returns **Partial** when mandatory energy data is available but optional weather or carbon data is unavailable.
- Returns **Failed** when mandatory energy data is unavailable.

### generateSustainabilityReport

- Generates a report for stored **Completed** or **Partial** assessments.
- Rejects report generation for **Failed** assessments.
- Adds `dataCompletenessNotes` whenever simulated or fallback data is used.

## Testing Triggers for Deterministic Scenarios

The adapters expose deterministic mock scenarios using the `property.location` and `property.propertyId` fields:

- `location` contains `no_weather` → weather unavailable
- `location` contains `no_carbon` → carbon unavailable
- `location` contains `green` → lower carbon factor used
- `location` contains `cold` → colder-weather note produced
- `propertyId` contains `FAIL-ENERGY` or `location` contains `no_energy` → energy failure

## Project Structure

- `backend/advisory-service` — Spring Boot implementation
- `openapi/openapi.yaml` — hand-curated OAS matching the implementation
- `testing/postman_collection.json` — Postman collection for happy path, partial path, failed path, and report generation
- `testing/test_cases.md` — compact test protocol
- `diagrams/*.puml` — REST-specific sequence diagrams with concrete requests and outcomes
- `documentation/*.md` — implementation, API design, and testing notes

## Running the Backend

```bash
cd backend/advisory-service
./mvnw spring-boot:run
```

Swagger UI:

- `http://localhost:8080/swagger-ui.html`

Generated OpenAPI endpoint:

- `http://localhost:8080/v3/api-docs`

## Notes on Alignment

The canonical endpoint names retain the GW1 operation names. Helper alias paths (`/assessments`, `/reports`) were kept for practical REST testing convenience, but both route sets execute the same backend logic.
