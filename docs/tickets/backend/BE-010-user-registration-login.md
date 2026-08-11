# BE-010: User Registration & Login

| Field | Value |
|-------|-------|
| **ID** | BE-010 |
| **Module** | `user-service` |
| **Sprint** | 1–2 |
| **Priority** | P0 |
| **Status** | Done |
| **PRD** | UM-01 |

## Summary

Email/phone + password registration and login for customers, restaurants, drivers, and admins.

## Acceptance Criteria

- [x] `POST /api/v1/auth/register` creates account with hashed password (BCrypt)
- [x] Duplicate email/phone returns 409 with stable error code
- [x] `POST /api/v1/auth/login` returns tokens on valid credentials
- [x] Invalid credentials return 401 without revealing which field failed
- [x] Verification email/SMS stub queued (full delivery in BE-063)
- [x] Server-side validation on all fields; password policy enforced

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register new user |
| POST | `/api/v1/auth/login` | Login with email/phone + password |

## Data Model

```sql
users (id UUID, email, phone, password_hash, role, email_verified, created_at)
```

## Dependencies

- BE-001, BE-003

## Definition of Done

- [x] Unit tests for password hashing and validation
- [x] Integration test: register → login happy path
- [x] OpenAPI or DTO docs updated
