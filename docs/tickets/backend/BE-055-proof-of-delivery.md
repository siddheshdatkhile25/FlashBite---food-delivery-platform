# BE-055: Proof of Delivery

| Field | Value |
|-------|-------|
| **ID** | BE-055 |
| **Module** | `delivery-service` |
| **Sprint** | 7–8 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | DL-05 |

## Summary

Order marked delivered only after customer confirmation or driver photo upload.

## Acceptance Criteria

- [ ] `POST /api/v1/driver/orders/{id}/deliver` with `proofType: CUSTOMER_CONFIRM | PHOTO`
- [ ] Photo stored in object storage; URL on delivery record
- [ ] Customer can confirm via `POST /api/v1/orders/{id}/confirm-delivery`
- [ ] Publishes `order.delivered` only after valid proof
- [ ] Order `delivered_at` timestamp set

## Dependencies

- BE-054, BE-033

## Definition of Done

- [ ] Integration test: deliver with photo → order DELIVERED
