# BE-085: System Health Monitoring Dashboard

| Field | Value |
|-------|-------|
| **ID** | BE-085 |
| **Module** | `analytics-service` |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | AP-06 |

## Summary

Admin view of service health, error rates, and latency percentiles.

## Acceptance Criteria

- [ ] `GET /api/v1/admin/health` aggregates actuator health from all services
- [ ] Error rate and P95 latency per service (from Prometheus or actuator metrics)
- [ ] Kafka consumer lag exposed
- [ ] Dashboard data refresh ≤ 30s
- [ ] Degraded service flagged when health != UP

## Dependencies

- BE-090, BE-060

## Definition of Done

- [ ] Integration test with mock actuator endpoints
- [ ] Documented alert thresholds for ops
