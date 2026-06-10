# BE-064: In-App Notification Center

| Field | Value |
|-------|-------|
| **ID** | BE-064 |
| **Module** | `notification-service` |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | NF-04 |

## Summary

Persisted in-app notifications with unread badge and mark-as-read.

## Acceptance Criteria

- [ ] `GET /api/v1/notifications` paginated list
- [ ] `GET /api/v1/notifications/unread-count` for badge
- [ ] `PATCH /api/v1/notifications/{id}/read` and `POST /api/v1/notifications/read-all`
- [ ] Notifications created by Kafka consumers alongside push/SMS/email
- [ ] Retention: 90 days

## Dependencies

- BE-060, BE-013

## Definition of Done

- [ ] Integration test: event → in-app notification → mark read
