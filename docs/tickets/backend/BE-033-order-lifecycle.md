# BE-033: Order Lifecycle State Machine

| Field | Value |
|-------|-------|
| **ID** | BE-033 |
| **Module** | `order-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | OM-05 |

## Summary

Central order state machine coordinating status across restaurant, payment, and delivery services via Kafka events.

## Acceptance Criteria

- [ ] States: `PLACED → CONFIRMED → PREPARING → READY → PICKED_UP → DELIVERED` (+ `CANCELLED`)
- [ ] Optimistic locking on status updates (`version` column)
- [ ] Kafka consumers update order on `payment.success`, `order.confirmed`, `driver.assigned`, `order.delivered`
- [ ] Invalid transitions logged and rejected
- [ ] Status history audit table optional but recommended

## Event Consumers

| Event | Action |
|-------|--------|
| `payment.success` | Keep PLACED or move to awaiting confirmation |
| `order.confirmed` | → CONFIRMED |
| `order.ready` | → READY |
| `driver.assigned` | Attach driverId |
| `order.delivered` | → DELIVERED |

## Dependencies

- BE-032, BE-060

## Definition of Done

- [ ] Unit tests for all valid/invalid transitions
- [ ] Integration test: event-driven full lifecycle
