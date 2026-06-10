# BE-052: Driver Accept/Reject Assignment

| Field | Value |
|-------|-------|
| **ID** | BE-052 |
| **Module** | `delivery-service` |
| **Sprint** | 7–8 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | DL-02 |

## Summary

Driver has 30s to accept or reject; timeout or reject triggers reassignment to next closest driver.

## Acceptance Criteria

- [ ] `POST /api/v1/driver/orders/{id}/accept` confirms assignment
- [ ] `POST /api/v1/driver/orders/{id}/reject` declines and triggers reassignment
- [ ] 30s timeout auto-rejects via scheduled job
- [ ] After 3 failed attempts: ops alert + customer ETA extension
- [ ] `GET /api/v1/driver/orders/current` returns active assignment

## Dependencies

- BE-051, BE-061

## Definition of Done

- [ ] Integration test: reject → next driver assigned
- [ ] Integration test: 3 failures → alert flag set
