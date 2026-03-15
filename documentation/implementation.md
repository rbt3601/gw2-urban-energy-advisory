# Implementation Notes

## Backend structure

The Spring Boot backend is organised into:

- `controller` — REST endpoints
- `service` — orchestration and lifecycle rules
- `adapter` — simulated external integrations
- `model` — request and response DTOs
- `internal` — stored assessment state used for report generation
- `exception` — API error handling

## Lifecycle logic

### Assessment lifecycle

1. validate request body
2. retrieve energy data (mandatory)
3. retrieve weather data (optional)
4. retrieve carbon data (optional)
5. derive status:
   - `Completed` if all datasets are available
   - `Partial` if energy is available but weather or carbon is missing
   - `Failed` if energy is unavailable

### Report generation lifecycle

1. validate report request body
2. load stored assessment by `assessmentId`
3. verify request status and `completedAt` against stored state
4. reject `Failed` assessments with HTTP 422
5. build summary metrics and recommendations
6. append completeness notes for simulated or missing data

## Intentional implementation choices

- Energy, weather, and carbon services are mocked deterministically so the team can test success and failure cases without relying on live APIs.
- Energy data is treated as mandatory to preserve the alternate-flow decision from GW0.
- Carbon emissions fall back to a default factor when carbon-intensity data is unavailable, but the report records this explicitly in `dataCompletenessNotes`.
