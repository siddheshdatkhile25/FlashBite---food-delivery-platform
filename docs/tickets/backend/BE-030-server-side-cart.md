# BE-030: Server-Side Cart

| Field | Value |
|-------|-------|
| **ID** | BE-030 |
| **Module** | `order-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | OM-01 |

## Summary

Persistent server-side cart per customer; single-restaurant cart at MVP.

## Acceptance Criteria

- [ ] `POST /api/v1/cart/items` adds item with quantity and customization notes
- [ ] `GET /api/v1/cart` returns current cart with line totals
- [ ] `PATCH /api/v1/cart/items/{id}` updates quantity/notes
- [ ] `DELETE /api/v1/cart/items/{id}` removes item
- [ ] Cart persists across sessions (PostgreSQL + optional Redis cache)
- [ ] Adding item from different restaurant clears cart or returns 409 (document decision)

## Data Model

```sql
carts (id, customer_id, restaurant_id, updated_at)
cart_items (id, cart_id, menu_item_id, quantity, notes, unit_price)
```

## Dependencies

- BE-010, BE-021, BE-003

## Definition of Done

- [ ] Integration test: add items → logout/login → cart persists
