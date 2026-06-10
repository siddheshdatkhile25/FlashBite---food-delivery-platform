# BE-035: Order History & Reorder

| Field | Value |
|-------|-------|
| **ID** | BE-035 |
| **Module** | `order-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | OM-07, SD-05 |

## Summary

Customer views past 6 months of orders and can reorder items into cart.

## Acceptance Criteria

- [ ] `GET /api/v1/orders/history?cursor=` returns paginated order history (6 months)
- [ ] `POST /api/v1/orders/{id}/reorder` adds previous items to cart (validates availability)
- [ ] History includes restaurant name, items, total, status, date
- [ ] Cursor-based pagination; max 50 per page

## Dependencies

- BE-032, BE-030

## Definition of Done

- [ ] Integration test: place order → history → reorder → cart populated
