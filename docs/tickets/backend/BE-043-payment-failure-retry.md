# BE-043: Payment Failure & Retry

| Field | Value |
|-------|-------|
| **ID** | BE-043 |
| **Module** | `payment-service` |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | PM-05 |

## Summary

Handle payment failures gracefully; hold order for 5 minutes with retry prompt.

## Acceptance Criteria

- [ ] Failed charge publishes `payment.failed`; order remains `PLACED` with `paymentPendingUntil`
- [ ] Order auto-cancelled if no successful payment within 5 minutes
- [ ] `POST /api/v1/payments/retry` retries with same `orderId` (idempotent)
- [ ] User-facing error codes: `CARD_DECLINED`, `INSUFFICIENT_FUNDS`, `GATEWAY_ERROR`
- [ ] Circuit breaker on gateway calls (Resilience4j)

## Dependencies

- BE-040, BE-033

## Definition of Done

- [ ] Integration test: fail → retry → success
- [ ] Integration test: timeout → order auto-cancelled
