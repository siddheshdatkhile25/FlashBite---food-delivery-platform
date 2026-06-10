# BE-061: Push Notifications

| Field | Value |
|-------|-------|
| **ID** | BE-061 |
| **Module** | `notification-service` |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | NF-01 |

## Summary

Push notifications for order status changes; delivered within 5s of event.

## Acceptance Criteria

- [ ] Kafka consumers on order/payment/delivery events trigger push
- [ ] Device token registration: `POST /api/v1/notifications/devices`
- [ ] FCM/APNs or web push integration (document provider)
- [ ] Template per event type (placed, confirmed, driver assigned, delivered)
- [ ] Respects user notification preferences (BE-065)

## Dependencies

- BE-060, BE-065

## Definition of Done

- [ ] Integration test with push mock: event → notification queued
