# Urban Energy Advisory Service -- GW2 Project

## Overview

This project implements a Service-Oriented Architecture (SOA) for an
Urban Energy Usage & Sustainability Advisory Service. The system allows
users to submit energy assessment requests and generate sustainability
advisory reports.

The backend service is implemented using Spring Boot (Java) and exposes
REST APIs for interacting with the advisory system.

The project also includes OpenAPI documentation, testing using Postman,
sequence diagrams, and project documentation.

------------------------------------------------------------------------

## System Architecture

The system follows a layered architecture:

Client → Controller → Service → Adapter → External Services

Layers

Controller\
Handles REST API requests.

Service\
Implements business logic and orchestrates services.

Adapter\
Simulates communication with external systems such as energy data,
weather data, and carbon intensity services.

Model\
Defines request and response data structures.

------------------------------------------------------------------------

## Project Structure

gw2-urban-energy-advisory │ ├── backend │ └── advisory-service │ ├──
src/main/java/com/energy/advisory_service │ │ ├── controller │ │ ├──
service │ │ ├── adapter │ │ └── model │ │ │ └── src/main/resources │ ├──
openapi │ └── openapi.yaml │ ├── testing │ ├── postman_collection.json │
└── test_cases.md │ ├── diagrams │ ├── assessment_sequence.puml │ └──
report_sequence.puml │ └── documentation └── final_report.docx

------------------------------------------------------------------------

## Technology Stack

Backend Framework: Spring Boot\
Language: Java\
API Documentation: OpenAPI / Swagger\
Testing: Postman\
Diagrams: PlantUML\
Build Tool: Maven

------------------------------------------------------------------------

## API Endpoints

### Submit Energy Assessment

POST /assessments

Example Request

{ "requestId": "REQ101", "propertyId": "P1001" }

Example Response

{ "assessmentId": "A-P1001", "assessmentStatus": "Completed" }

------------------------------------------------------------------------

### Generate Sustainability Report

POST /reports

Example Request

{ "assessmentId": "A-P1001", "assessmentStatus": "Completed" }

Example Response

{ "reportId": "R101" }

------------------------------------------------------------------------

## Running the Project

Clone Repository

git clone `<repository-url>`{=html}

Navigate to Backend

cd backend/advisory-service

Run Application

./mvnw spring-boot:run

Server will start at: http://localhost:8080

------------------------------------------------------------------------

## Swagger API Documentation

After running the service open: http://localhost:8080/swagger-ui.html

The OpenAPI specification is available at:
http://localhost:8080/v3/api-docs

------------------------------------------------------------------------

## Team Responsibilities

Backend Development Shailesh Mrudulaa Honey

API Design & OpenAPI Documentation Anirudh Srinadh Nischitha

Testing & Validation Rajesh Akshay Suraj

------------------------------------------------------------------------

## Deliverables

Spring Boot backend implementation\
OpenAPI specification\
REST API documentation\
Postman testing results\
Sequence diagrams\
Final report

------------------------------------------------------------------------

Urban Energy Advisory Service -- Group Project
