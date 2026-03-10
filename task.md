# GW2 Project -- Task Distribution

## Team Structure

The project is divided into three teams:

1.  Backend Development Team
2.  API Design & Documentation Team
3.  Testing & Validation Team

Work will proceed in three phases so each team can build on the previous
team's output.

------------------------------------------------------------------------

# Team 1 -- Backend Development

Members - Shailesh - Mrudulaa - Honey

Objective Implement the backend logic using Spring Boot.

Project Folder backend/advisory-service

Tasks

1.  Implement REST Controllers

Files

controller/AssessmentController.java\
controller/ReportController.java

Responsibilities - Implement API endpoints - Accept request objects -
Return response objects

Endpoints

POST /assessments\
POST /reports

------------------------------------------------------------------------

2.  Implement Service Layer

Files

service/AdvisoryService.java\
service/ReportService.java

Responsibilities

-   Implement business logic
-   Call adapter services
-   Process request data

------------------------------------------------------------------------

3.  Implement External Service Adapters

Files

adapter/EnergyServiceAdapter.java\
adapter/WeatherServiceAdapter.java\
adapter/CarbonServiceAdapter.java

Responsibilities

-   Simulate external systems
-   Provide energy, weather, and carbon data

------------------------------------------------------------------------

4.  Implement Data Models

Folder

model/request\
model/response

Files

AssessmentRequest.java\
AdvisoryAssessmentResponse.java\
AdvisoryReportResponse.java

------------------------------------------------------------------------

5.  Integrate Swagger

Add dependency in pom.xml

Then verify Swagger UI:

http://localhost:8080/swagger-ui.html

------------------------------------------------------------------------

Deliverables

Working backend with

-   REST APIs running
-   Swagger documentation
-   Request and response models

------------------------------------------------------------------------

# Team 2 -- API Design & OpenAPI Documentation

Members - Anirudh - Srinadh - Nischitha

Objective

Validate the API structure and produce the OpenAPI specification.

Tasks

1.  Run the backend service

2.  Open Swagger UI

http://localhost:8080/swagger-ui.html

3.  Review endpoints

POST /assessments\
POST /reports

4.  Export OpenAPI specification

http://localhost:8080/v3/api-docs

5.  Save it as

openapi/openapi.yaml

------------------------------------------------------------------------

API Documentation

Create documentation explaining:

-   API purpose
-   request format
-   response format
-   example payloads

Example

POST /assessments

Request

{ "requestId": "REQ101", "propertyId": "P1001" }

Response

{ "assessmentId": "A-P1001", "assessmentStatus": "Completed" }

Deliverables

openapi/openapi.yaml\
openapi/api_examples.md

------------------------------------------------------------------------

# Team 3 -- Testing & Validation

Members - Rajesh - Akshay - Suraj

Objective

Test the APIs and validate system functionality.

Tasks

1.  Create Postman collection

testing/postman_collection.json

2.  API Test Cases

Test 1 -- Valid assessment request

POST /assessments

Test 2 -- Invalid request data

Test 3 -- Generate sustainability report

POST /reports

Test 4 -- Error handling scenario

------------------------------------------------------------------------

Record Test Results

testing/test_cases.md\
testing/test_results.md

Include

-   request data
-   response
-   screenshots

------------------------------------------------------------------------

# Sequence Diagrams

Folder

diagrams/

Files

assessment_sequence.puml\
report_sequence.puml

These diagrams show

Client → Controller → Service → Adapter

------------------------------------------------------------------------

# Final Workflow

Phase 1 Backend Development

Phase 2 API Documentation & OpenAPI generation

Phase 3 Testing and validation

------------------------------------------------------------------------

# Project Lead

Rajesh

Responsibilities

-   create project structure
-   coordinate teams
-   review implementation
-   ensure integration works

------------------------------------------------------------------------

# Expected Final Deliverables

-   Spring Boot backend service
-   OpenAPI specification
-   API documentation
-   Postman test collection
-   REST sequence diagrams
-   Final project report
