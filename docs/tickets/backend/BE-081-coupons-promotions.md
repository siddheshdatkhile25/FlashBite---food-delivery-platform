# BE-081: Coupons & Promotions

| Field | Value |
|-------|-------|
| **ID** | BE-081 |
| **Module** | `order-service` + `payment-service` |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | PC-01 – PC-04 |

## Summary

Admin-managed coupons and restaurant promotions applied at checkout.

## Acceptance Criteria

- [ ] Admin CRUD coupons: flat/percentage, min order, max discount, expiry, usage limit
- [ ] `POST /api/v1/cart/apply-coupon` validates and returns updated quote
- [ ] Restaurant-specific promotions with banner metadata
- [ ] First-order discount auto-applied for new users
- [ ] Coupon usage tracked; expired/ exhausted returns clear error

## Admin API

| Method | Endpoint | Description |
|--------|----------|-------------|
| CRUD | `/api/v1/admin/coupons` | Manage coupons |

## Dependencies

- BE-041, BE-083

## Definition of Done

- [ ] Unit tests for discount calculation edge cases
