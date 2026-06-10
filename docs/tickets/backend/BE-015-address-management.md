# BE-015: Saved Delivery Addresses

| Field | Value |
|-------|-------|
| **ID** | BE-015 |
| **Module** | `user-service` |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | UM-04, OM-03 |

## Summary

CRUD for customer delivery addresses used at checkout.

## Acceptance Criteria

- [ ] `GET /api/v1/users/me/addresses` lists addresses
- [ ] `POST /api/v1/users/me/addresses` creates address with lat/lng, label, line1, city, zip
- [ ] `PATCH /api/v1/users/me/addresses/{id}` updates address
- [ ] `DELETE /api/v1/users/me/addresses/{id}` soft-deletes address
- [ ] One address can be marked `default`
- [ ] Max addresses per user enforced (e.g. 10)

## Data Model

```sql
addresses (id, user_id, label, line1, line2, city, state, zip, lat, lng, is_default)
```

## Dependencies

- BE-010, BE-013

## Definition of Done

- [ ] Integration test: CRUD + default address switching
