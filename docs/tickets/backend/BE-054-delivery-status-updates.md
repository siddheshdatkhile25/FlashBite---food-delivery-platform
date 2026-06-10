# BE-054: Delivery Status Updates

| Field | Value |
|-------|-------|
| **ID** | BE-054 |
| **Module** | `delivery-service` |
| **Sprint** | 7–8 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | DL-04, OM-05 |

## Summary

Driver updates delivery status: picked up → en route → arriving → delivered.

## Acceptance Criteria

- [ ] `PATCH /api/v1/driver/orders/{id}/status` with valid transitions
- [ ] `READY → PICKED_UP → EN_ROUTE → ARRIVING → DELIVERED`
- [ ] Each change updates order-service via event or REST
- [ ] Push notification to customer within 5s (BE-061)
- [ ] `PICKED_UP` syncs order status to `PICKED_UP`

## Dependencies

- BE-052, BE-033, BE-061

## Definition of Done

- [ ] Integration test: status progression updates order record
