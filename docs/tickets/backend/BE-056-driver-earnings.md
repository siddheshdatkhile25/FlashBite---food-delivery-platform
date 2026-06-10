# BE-056: Driver Earnings Dashboard

| Field | Value |
|-------|-------|
| **ID** | BE-056 |
| **Module** | `delivery-service` |
| **Sprint** | 7–8 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | DL-06 |

## Summary

Per-order earnings breakdown and daily/weekly summary for drivers.

## Acceptance Criteria

- [ ] `GET /api/v1/driver/earnings?period=daily|weekly` returns totals
- [ ] Per-order: base pay, tips, bonus, completed_at
- [ ] Scoped to authenticated driver only
- [ ] Earnings calculated on `order.delivered` event

## Dependencies

- BE-055, BE-060

## Definition of Done

- [ ] Integration test: complete delivery → earnings updated
