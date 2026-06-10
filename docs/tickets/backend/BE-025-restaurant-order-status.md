# BE-025: Restaurant Order Status Updates

| Field | Value |
|-------|-------|
| **ID** | BE-025 |
| **Module** | `restaurant-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | RM-06 |

## Summary

Restaurant updates order status: confirm → preparing → ready for pickup. Triggers customer notifications and delivery assignment.

## Acceptance Criteria

- [ ] `PATCH /api/v1/restaurant/orders/{id}/status` accepts valid transitions only
- [ ] Valid transitions: `PLACED→CONFIRMED`, `CONFIRMED→PREPARING`, `PREPARING→READY`
- [ ] Publishes `order.confirmed` and `order.ready` Kafka events
- [ ] `order.ready` triggers delivery assignment (BE-051)
- [ ] Invalid transition returns 409 with current status
- [ ] Customer notified within 5s (BE-061)

## Events

| Event | When |
|-------|------|
| `order.confirmed` | Restaurant confirms |
| `order.ready` | Food ready for pickup |

## Dependencies

- BE-024, BE-033, BE-060

## Definition of Done

- [ ] Integration test: full status progression with event assertions
