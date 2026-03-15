# Testing Notes

## Test strategy

The backend is designed for reproducible manual and automated testing.

### Deterministic adapter triggers

- `FAIL-ENERGY` in `property.propertyId` → failed assessment
- `no_carbon` in `property.location` → partial assessment without carbon dataset
- `no_weather` in `property.location` → partial assessment without weather dataset
- `green` in `property.location` → lower carbon factor
- `cold` in `property.location` → colder-weather context note

## Coverage goals

- success path for assessment
- partial-data path for assessment
- failure path for assessment
- successful report generation from stored assessment
- validation failure for malformed JSON or missing required fields

## Automated tests

`AdvisoryServiceApplicationTests` includes MockMvc tests covering:

- completed assessment submission
- partial assessment submission
- failed assessment submission
- unknown assessment lookup

## Manual Postman tests

The collection in `testing/postman_collection.json` covers the same paths and stores IDs from successful calls as collection variables for report generation.
