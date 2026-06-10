# BE-031: Cart Validation

| Field | Value |
|-------|-------|
| **ID** | BE-031 |
| **Module** | `order-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | OM-02 |

## Summary

Validate cart before checkout: restaurant open, items in stock, minimum order amount.

## Acceptance Criteria

- [ ] `POST /api/v1/cart/validate` returns validation result with per-item errors
- [ ] Checks: restaurant `isOpen`, item `available`, prices match current menu
- [ ] Minimum order amount enforced (configurable per restaurant)
- [ ] Stale prices refreshed from restaurant-service
- [ ] Validation errors include stable codes (`ITEM_UNAVAILABLE`, `RESTAURANT_CLOSED`, etc.)

## Dependencies

- BE-030, BE-022, BE-023

## Definition of Done

- [ ] Unit tests for each validation rule
- [ ] Integration test: unavailable item → validation fails
