# BE-050: Driver Availability Toggle

| Field | Value |
|-------|-------|
| **ID** | BE-050 |
| **Module** | `delivery-service` |
| **Sprint** | 7–8 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | DL-07 |

## Summary

Drivers toggle online/offline; only online drivers receive assignments.

## Acceptance Criteria

- [ ] `PATCH /api/v1/driver/status` body `{ "online": true|false }`
- [ ] Offline drivers excluded from assignment queries
- [ ] Last known location retained when going offline
- [ ] Status change logged for analytics

## Dependencies

- BE-013, BE-003

## Definition of Done

- [ ] Integration test: offline driver not in assignment pool
