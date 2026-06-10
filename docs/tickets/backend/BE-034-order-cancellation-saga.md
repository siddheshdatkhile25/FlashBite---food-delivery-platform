# BE-034: Order Cancellation & Saga Compensation

| Field | Value |
|-------|-------|
| **ID** | BE-034 |
| **Module** | `order-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | OM-06, §5.2 |

## Summary

Cancellation rules with saga compensation: refund payment, notify parties, unassign driver.

## Acceptance Criteria

- [ ] `POST /api/v1/orders/{id}/cancel` enforces cancellation policy
- [ ] Within 60s of placement OR before restaurant confirms: immediate cancel + refund
- [ ] After confirm but before PREPARING: cancel with restaurant notification
- [ ] PREPARING or later: return 409 with reason
- [ ] Publishes `order.cancelled` with `refundAmount`
- [ ] Payment service processes refund (BE-042); driver unassigned if assigned

## Saga Flow

```
cancel request → validate policy → order.cancelled event
  → payment-service refund → notification-service confirm
  → delivery-service unassign driver
```

## Dependencies

- BE-033, BE-042, BE-060

## Definition of Done

- [ ] Integration test: cancel before confirm → refund triggered
- [ ] Integration test: cancel after preparing → denied
