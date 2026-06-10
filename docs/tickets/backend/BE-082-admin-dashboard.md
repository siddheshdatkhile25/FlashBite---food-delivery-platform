# BE-082: Admin Live Metrics Dashboard

| Field | Value |
|-------|-------|
| **ID** | BE-082 |
| **Module** | `analytics-service` |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | AP-01 |

## Summary

Live platform metrics: active orders, online drivers, revenue; refreshes every 30s.

## Acceptance Criteria

- [ ] `GET /api/v1/admin/dashboard` returns KPIs
- [ ] Metrics: activeOrders, onlineDrivers, todayRevenue, ordersPerHour
- [ ] Kafka consumers aggregate business metrics in real time
- [ ] Data acceptable within 30s staleness
- [ ] Admin role required

## Dependencies

- BE-060, BE-013

## Definition of Done

- [ ] Integration test with seeded events verifies counts
