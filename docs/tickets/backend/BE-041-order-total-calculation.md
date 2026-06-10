# BE-041: Order Total Calculation

| Field | Value |
|-------|-------|
| **ID** | BE-041 |
| **Module** | `payment-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | PM-03 |

## Summary

Calculate order total: items + taxes + delivery fee − discounts; itemized breakdown before payment.

## Acceptance Criteria

- [ ] `POST /api/v1/payments/quote` returns breakdown: subtotal, tax, deliveryFee, discount, total
- [ ] Tax rate configurable per region (single city at MVP)
- [ ] Delivery fee based on distance or flat rate (document formula)
- [ ] Discount from coupon applied when provided (BE-081)
- [ ] Totals match at checkout and charge (no drift)

## Dependencies

- BE-030, BE-015

## Definition of Done

- [ ] Unit tests for tax, fee, discount combinations
