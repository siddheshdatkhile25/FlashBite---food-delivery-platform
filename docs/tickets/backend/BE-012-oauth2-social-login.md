# BE-012: OAuth2 Social Login

| Field | Value |
|-------|-------|
| **ID** | BE-012 |
| **Module** | `user-service` |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | UM-02 |

## Summary

One-click sign-in via Google and Facebook OAuth2; auto-create or link existing accounts.

## Acceptance Criteria

- [ ] `GET /api/v1/auth/oauth2/{provider}` initiates OAuth flow
- [ ] Callback handler creates user on first login or links to existing email
- [ ] Profile auto-populated: name, email, avatar URL
- [ ] Returns same JWT token pair as password login
- [ ] OAuth client secrets from env vars only

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/auth/oauth2/google` | Google OAuth redirect |
| GET | `/api/v1/auth/oauth2/facebook` | Facebook OAuth redirect |
| GET | `/api/v1/auth/oauth2/callback/{provider}` | OAuth callback |

## Dependencies

- BE-010, BE-011

## Definition of Done

- [ ] Integration test with OAuth mock/stub provider
- [ ] Account linking documented for duplicate email case
