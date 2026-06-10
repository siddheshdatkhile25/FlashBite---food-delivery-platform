# BE-020: Restaurant Registration & Profile

| Field | Value |
|-------|-------|
| **ID** | BE-020 |
| **Module** | `restaurant-service` |
| **Sprint** | 1–2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | RM-01, AP-02 |

## Summary

Restaurant partner onboarding: profile setup with name, address, cuisine, hours, logo. Initial status `PENDING_APPROVAL` until admin approves (BE-083).

## Acceptance Criteria

- [ ] `POST /api/v1/restaurant/register` creates restaurant linked to restaurant user
- [ ] `GET /api/v1/restaurant/profile` returns own restaurant profile
- [ ] `PATCH /api/v1/restaurant/profile` updates name, address, cuisine tags, logo URL, contact
- [ ] Geo coordinates (lat/lng) stored for search indexing
- [ ] Restaurant hidden from customer search until `APPROVED` status

## Data Model

```sql
restaurants (id, owner_user_id, name, address, lat, lng, cuisine, logo_url, status, created_at)
```

## Dependencies

- BE-003, BE-013

## Definition of Done

- [ ] Integration test: register restaurant → profile CRUD
- [ ] Publishes `restaurant.created` event for search indexing (BE-070)
