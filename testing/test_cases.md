# GW2 Test Cases

## TC1 — Completed assessment

**Endpoint**: `POST /submitEnergyAssessment`

**Input summary**:
- location: `Leicester green cold`
- propertyType: `home`
- granularity: `hourly`

**Expected result**:
- HTTP 201
- `assessmentStatus = Completed`
- energy, weather, and carbon dataset objects present

## TC2 — Partial assessment because carbon data is unavailable

**Endpoint**: `POST /submitEnergyAssessment`

**Input summary**:
- location: `Leicester no_carbon`

**Expected result**:
- HTTP 201
- `assessmentStatus = Partial`
- energy dataset present
- carbon dataset omitted

## TC3 — Failed assessment because energy data is unavailable

**Endpoint**: `POST /submitEnergyAssessment`

**Input summary**:
- propertyId: `FAIL-ENERGY-001`

**Expected result**:
- HTTP 201
- `assessmentStatus = Failed`
- `errorMessage` present
- no `completedAt`
- no dataset objects

## TC4 — Generate report from completed assessment

**Flow**:
1. create completed assessment
2. pass returned `assessmentId`, `assessmentStatus`, and `completedAt` to `POST /generateSustainabilityReport`

**Expected result**:
- HTTP 201
- report contains `summaryMetrics`
- report contains at least one `recommendation`
- `dataCompletenessNotes` may still note simulated energy input

## TC5 — Invalid request body

**Endpoint**: `POST /submitEnergyAssessment`

**Input summary**:
- missing `property`
- malformed `createdAt`

**Expected result**:
- HTTP 400
- validation error payload returned
