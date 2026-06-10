# BE-026: Restaurant Analytics

| Field | Value |
|-------|-------|
| **ID** | BE-026 |
| **Module** | `restaurant-service` |
| **Sprint** | 13–14 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | RM-07 |

## Summary

Revenue and order analytics dashboard for restaurant partners with daily/weekly/monthly views and CSV export.

## Acceptance Criteria

- [ ] `GET /api/v1/restaurant/analytics?period=daily|weekly|monthly` returns revenue, order count, AOV
- [ ] `GET /api/v1/restaurant/analytics/export?format=csv` downloads report
- [ ] Data scoped to authenticated restaurant only
- [ ] Aggregations precomputed or cached (acceptable 5-min lag)

## Dependencies

- BE-032, BE-040

## Definition of Done

- [ ] Integration test with seed orders verifies totals
