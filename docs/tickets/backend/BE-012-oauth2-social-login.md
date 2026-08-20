# BE-012: OAuth2 Social Login

| Field | Value |
|-------|-------|
| **ID** | BE-012 |
| **Module** | `user-service` |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Done |
| **PRD** | UM-02 |

## Summary

One-click sign-in via Google OAuth2; auto-create or link existing accounts.

## Acceptance Criteria

- [x] `GET /api/v1/auth/oauth2/{provider}` initiates OAuth flow
- [x] Callback handler creates user on first login or links to existing email
- [x] Profile auto-populated: name, email, avatar URL
- [x] Returns same JWT token pair as password login
- [x] OAuth client secrets from env vars only

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/auth/oauth2/google` | Google OAuth redirect |
| GET | `/api/v1/auth/oauth2/callback/{provider}` | OAuth callback |

## Dependencies

- BE-010, BE-011

## Definition of Done

- [ ] Integration test with OAuth mock/stub provider
- [x] Account linking documented for duplicate email case
