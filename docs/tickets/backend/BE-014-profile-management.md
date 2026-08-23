# BE-014: Profile Management

| Field | Value |
|-------|-------|
| **ID** | BE-014 |
| **Module** | `user-service` |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | UM-04 |

## Summary

Users update name and photo; changes reflected immediately.

## Acceptance Criteria

- [ ] `GET /api/v1/users/me` returns current user profile
- [ ] `PATCH /api/v1/users/me` updates name, photo URL
- [ ] Updates validated server-side; PII fields sanitized

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/users/me` | Get profile |
| PATCH | `/api/v1/users/me` | Update profile |

## Dependencies

- BE-010, BE-013

## Definition of Done

- [ ] Integration test: update profile → GET reflects changes
