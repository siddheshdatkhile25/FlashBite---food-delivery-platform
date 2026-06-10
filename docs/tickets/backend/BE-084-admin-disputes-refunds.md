# BE-084: Admin Disputes & Manual Refunds

| Field | Value |
|-------|-------|
| **ID** | BE-084 |
| **Module** | `payment-service` + `order-service` |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | AP-05 |

## Summary

Dispute resolution with full order audit trail and manual refund issuance.

## Acceptance Criteria

- [ ] `GET /api/v1/admin/orders/{id}` full audit: status history, payments, delivery, notifications
- [ ] `POST /api/v1/admin/refunds` issues full/partial refund with reason
- [ ] Refund requires admin role; logged in audit trail
- [ ] Customer notified of manual refund (BE-063)

## Dependencies

- BE-042, BE-033, BE-083

## Definition of Done

- [ ] Integration test: admin refund → payment refunded + audit entry
