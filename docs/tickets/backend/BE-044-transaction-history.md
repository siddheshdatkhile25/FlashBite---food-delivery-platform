# BE-044: Transaction History

| Field | Value |
|-------|-------|
| **ID** | BE-044 |
| **Module** | `payment-service` |
| **Sprint** | 13–14 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | PM-06 |

## Summary

Transaction history for customers and restaurants with PDF/CSV export.

## Acceptance Criteria

- [ ] `GET /api/v1/payments/history` returns paginated transactions for current user
- [ ] `GET /api/v1/payments/history/export?format=csv|pdf`
- [ ] Includes: date, orderId, amount, method, status
- [ ] Restaurant sees payouts; customer sees charges/refunds

## Dependencies

- BE-040, BE-013

## Definition of Done

- [ ] Integration test: charge + refund appear in history
