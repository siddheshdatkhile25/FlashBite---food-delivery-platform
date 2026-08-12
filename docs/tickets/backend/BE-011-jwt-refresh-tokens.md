# BE-011: JWT Access & Refresh Tokens

| Field | Value |
|-------|-------|
| **ID** | BE-011 |
| **Module** | `user-service` |
| **Sprint** | 1–2 |
| **Priority** | P0 |
| **Status** | Done |
| **PRD** | UM-05 |

## Summary

JWT-based session management: 15-minute access tokens, 7-day refresh tokens with rotation and Redis-backed revocation.

## Acceptance Criteria

- [x] Access token expires in 15 minutes; refresh in 7 days
- [x] `POST /api/v1/auth/refresh` issues new token pair and invalidates old refresh token
- [x] `POST /api/v1/auth/logout` revokes refresh token
- [x] Refresh token reuse detection invalidates token family (security)
- [x] JWT claims include `sub`, `role`, `iat`, `exp`
- [x] Tokens stored in Redis for revocation lookup

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/refresh` | Refresh access token |
| POST | `/api/v1/auth/logout` | Revoke session |

## Dependencies

- BE-010, BE-003 (Redis via docker-compose)

## Definition of Done

- [x] Integration test: login → refresh → logout
- [x] Expired access token rejected; valid refresh succeeds
