# BE-065: Notification Preferences

| Field | Value |
|-------|-------|
| **ID** | BE-065 |
| **Module** | `notification-service` + `user-service` |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | NF-05 |

## Summary

Per-user opt-in/opt-out per channel (push, SMS, email) and event category.

## Acceptance Criteria

- [ ] `GET /api/v1/users/me/notification-preferences`
- [ ] `PUT /api/v1/users/me/notification-preferences` with channel toggles
- [ ] All notification senders check preferences before dispatch
- [ ] Critical SMS (delivered) cannot be fully disabled (configurable minimum)

## Dependencies

- BE-014, BE-061, BE-062, BE-063

## Definition of Done

- [ ] Integration test: disable push → no push sent on event
