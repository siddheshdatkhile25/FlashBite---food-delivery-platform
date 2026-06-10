# BE-051: Automatic Driver Assignment

| Field | Value |
|-------|-------|
| **ID** | BE-051 |
| **Module** | `delivery-service` |
| **Sprint** | 7–8 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | DL-01 |

## Summary

Assign nearest available online driver within 60s of restaurant marking order READY.

## Acceptance Criteria

- [ ] Kafka consumer on `order.ready` triggers assignment
- [ ] Geo-query finds online drivers within radius (MongoDB 2dsphere or PostGIS)
- [ ] Closest driver selected by haversine distance
- [ ] Assignment record created with status `PENDING_ACCEPTANCE`
- [ ] Push notification sent to driver (BE-061)
- [ ] If no drivers: alert ops after 3 min (configurable)

## Events

| Topic | When |
|-------|------|
| `driver.assigned` | Assignment created (pending accept) |

## Dependencies

- BE-025, BE-050, BE-053, BE-060

## Definition of Done

- [ ] Integration test: order ready → nearest driver assigned
- [ ] Unit test for distance ranking
