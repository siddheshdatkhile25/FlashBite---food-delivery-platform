# BE-024: Incoming Orders (Restaurant View)

| Field | Value |
|-------|-------|
| **ID** | BE-024 |
| **Module** | `restaurant-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | RM-05 |

## Summary

Restaurant dashboard lists incoming orders in real time; orders appear within 2s of placement.

## Acceptance Criteria

- [ ] `GET /api/v1/restaurant/orders` returns active orders for authenticated restaurant
- [ ] Filter by status: `PLACED`, `CONFIRMED`, `PREPARING`, `READY`
- [ ] Orders include items, customer notes, total, placed_at
- [ ] Kafka consumer on `order.placed` / `payment.success` adds order to restaurant queue
- [ ] Pagination with cursor; default sort `placed_at DESC`

## Dependencies

- BE-032, BE-060

## Definition of Done

- [ ] Integration test: place order → restaurant list includes it within 2s
