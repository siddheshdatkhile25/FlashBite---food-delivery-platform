# BE-045: Restaurant Payout & Settlement

| Field | Value |
|-------|-------|
| **ID** | BE-045 |
| **Module** | `payment-service` |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | PM-07 |

## Summary

Weekly settlement summary for restaurants with commission deduction and line-item detail.

## Acceptance Criteria

- [ ] `GET /api/v1/restaurant/payouts` lists settlement periods
- [ ] `GET /api/v1/restaurant/payouts/{id}` shows line items per order
- [ ] Commission rate configurable (open question in PRD §13)
- [ ] Settlement batch job runs weekly; status `PENDING`, `PAID`

## Dependencies

- BE-040, BE-026

## Definition of Done

- [ ] Unit test for commission calculation
- [ ] Settlement job idempotent per period
