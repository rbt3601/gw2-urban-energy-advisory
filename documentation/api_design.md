# API Design Notes

## Canonical operations

The backend keeps the two GW1-selected operations as canonical endpoint names:

- `POST /submitEnergyAssessment`
- `POST /generateSustainabilityReport`

Alias paths (`/assessments`, `/reports`) are included only for practical REST-style testing and local iteration.

## Contract alignment decisions

### submitEnergyAssessment request

The request uses the nested GW1 shape:

- `requestId`
- `createdAt`
- `property`
- `timeWindow`

This replaces the earlier flattened prototype that mixed `property` and `timeWindow` fields into the root object.

### submitEnergyAssessment response

The response now follows the GW1 dataset-oriented structure:

- `assessmentId`
- `startedAt`
- `completedAt` (only for completed and partial cases)
- `assessmentStatus`
- `energyConsumptionData`
- `weatherContextData`
- `carbonIntensityData`
- `errorMessage` (failed only)

### generateSustainabilityReport request

The request follows the GW1 reduced model with:

- `assessmentId`
- `assessmentStatus`
- `completedAt`

The implementation checks these values against the stored assessment to prevent drift between client and server state.

### generateSustainabilityReport response

The response includes:

- `reportId`
- `generatedAt`
- `reportVersion`
- `summaryMetrics`
- `recommendations`
- optional `contextNotes`
- optional `dataCompletenessNotes`

## Error model

Validation and runtime errors return a structured payload containing:

- `timestamp`
- `status`
- `error`
- `message`
- `path`
- `validationErrors` when applicable
