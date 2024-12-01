# Better Integration Test

## Overview

This project is example on how to write integration test using springboot, hopefully this project will help you to write
a better integration test with better seeding data and adding wiremock stubbing

## Sample Services

### 1. User Service

#### Description
The User Service manages user-related operations such as registration, authentication, and profile management.

#### Endpoints
- `POST /api/better-integration-test/user`: Register a new user.
- `POST /api/better-integration-test/user/login`: Authenticate a user.
- `GET /api/better-integration-test/user/{id}`: Retrieve user details by ID.
- `PATCH /api/better-integration-test/user/{id}`: Update data if we want ban the user or not.
