# BE-042: Refund Processing

| Field | Value |
|-------|-------|
| **ID** | BE-042 |
| **Module** | `payment-service` |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | PM-04 |

## Summary

Full and partial refunds triggered by cancellation or admin action; idempotent processing.

## Acceptance Criteria

- [ ] Kafka consumer on `order.cancelled` initiates refund
- [ ] `POST /api/v1/admin/refunds` for manual refunds (BE-084)
- [ ] Refund record: amount, reason, status, gateway_refund_id
- [ ] Idempotent on `orderId` + refund type
- [ ] Refund initiated within 24h; status tracked until gateway confirms

## Data Model

```sql
refunds (id, payment_id, order_id, amount, reason, status, gateway_refund_id, created_at)
```

## Dependencies

- BE-040, BE-034, BE-060

## Definition of Done

- [ ] Integration test: cancel order → refund record created
- [ ] Duplicate refund request is no-op
