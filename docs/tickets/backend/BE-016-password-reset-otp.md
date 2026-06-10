# BE-016: Password Reset via Email OTP

| Field | Value |
|-------|-------|
| **ID** | BE-016 |
| **Module** | `user-service` |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | UM-06 |

## Summary

Password reset flow using email OTP stored in Redis (10-minute TTL, one-time use).

## Acceptance Criteria

- [ ] `POST /api/v1/auth/forgot-password` sends OTP to registered email
- [ ] `POST /api/v1/auth/reset-password` validates OTP and sets new password
- [ ] OTP valid for 10 minutes; invalidated after use
- [ ] Rate limit on forgot-password (e.g. 3/hour per email)
- [ ] Response does not reveal whether email exists

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/forgot-password` | Request OTP |
| POST | `/api/v1/auth/reset-password` | Reset with OTP + new password |

## Dependencies

- BE-010, Redis

## Definition of Done

- [ ] Integration test: request OTP → reset → login with new password
- [ ] Expired OTP rejected
