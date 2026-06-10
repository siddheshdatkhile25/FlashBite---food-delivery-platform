# BE-040: Payment Gateway Integration

| Field | Value |
|-------|-------|
| **ID** | BE-040 |
| **Module** | `payment-service` |
| **Sprint** | 3–4 (stub), 5–6 (full) |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | PM-01, PM-02 |

## Summary

Integrate Stripe or Razorpay for card and UPI payments; PCI-DSS compliant tokenization.

## Acceptance Criteria

- [ ] Sprint 3–4: stub payment returns success/failure for dev
- [ ] Sprint 5–6: real gateway integration with webhook handler
- [ ] At least 2 methods at MVP: card + one of UPI/wallet/COD
- [ ] No raw card numbers stored; gateway tokens only
- [ ] `POST /api/v1/payments/charge` idempotent on `orderId`
- [ ] Publishes `payment.success` or `payment.failed`
- [ ] Webhook signature verification

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/payments/charge` | Charge for order |
| POST | `/api/v1/payments/webhook` | Gateway webhook |

## Events

| Topic | When |
|-------|------|
| `payment.success` | Charge succeeded |
| `payment.failed` | Charge failed |

## Dependencies

- BE-032, BE-060

## Definition of Done

- [ ] Integration test with gateway sandbox
- [ ] Audit log entry per payment attempt
