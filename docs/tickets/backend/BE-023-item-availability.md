# BE-023: Toggle Item Availability

| Field | Value |
|-------|-------|
| **ID** | BE-023 |
| **Module** | `restaurant-service` |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | RM-04 |

## Summary

Restaurant toggles menu item in/out of stock; reflected in customer UI immediately.

## Acceptance Criteria

- [ ] `PATCH /api/v1/restaurant/menu/items/{id}/availability` sets `available: true/false`
- [ ] Unavailable items returned in menu with `available: false` (not hidden)
- [ ] Cart validation rejects unavailable items (BE-031)
- [ ] Publishes `menu.item.availability.changed` for search cache invalidation

## Dependencies

- BE-021

## Definition of Done

- [ ] Integration test: toggle off → public menu shows unavailable
