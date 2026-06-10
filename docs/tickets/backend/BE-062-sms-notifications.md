# BE-062: SMS Notifications

| Field | Value |
|-------|-------|
| **ID** | BE-062 |
| **Module** | `notification-service` |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | NF-02 |

## Summary

SMS for critical events (order placed, delivered) via Twilio or AWS SNS.

## Acceptance Criteria

- [ ] SMS sent on `order.placed` and `order.delivered` by default
- [ ] Twilio/SNS client with env-based credentials
- [ ] Rate limit per phone number
- [ ] Delivery status logged; failures retried once
- [ ] Opt-out honored via BE-065

## Dependencies

- BE-060, BE-065

## Definition of Done

- [ ] Integration test with SMS stub provider
