# BE-032: Checkout & Place Order

| Field | Value |
|-------|-------|
| **ID** | BE-032 |
| **Module** | `order-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | OM-03, OM-04 |

## Summary

Checkout with delivery address, order summary, and order placement with estimated delivery time.

## Acceptance Criteria

- [ ] `POST /api/v1/orders` places order from cart with `addressId` and `paymentMethodId`
- [ ] Order created in `PLACED` status with itemized breakdown
- [ ] Cart cleared on successful placement
- [ ] `Idempotency-Key` header prevents duplicate orders
- [ ] Publishes `order.placed` event
- [ ] Confirmation response within 2s including static ETA estimate
- [ ] Delivery address snapshotted as JSONB on order row

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/orders` | Place order |
| GET | `/api/v1/orders/{id}` | Order details |

## Events

| Topic | Producer |
|-------|----------|
| `order.placed` | order-service |

## Dependencies

- BE-031, BE-015, BE-041

## Definition of Done

- [ ] Integration test: validate cart → place order → GET details
- [ ] Idempotency test: duplicate key returns same order
