# BE-063: Email Notifications

| Field | Value |
|-------|-------|
| **ID** | BE-063 |
| **Module** | `notification-service` |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | NF-03 |

## Summary

Transactional emails: order confirmation, receipts, password reset OTP, promotions.

## Acceptance Criteria

- [ ] Email sent within 1 min of triggering event
- [ ] Templates: order confirmation, receipt, forgot-password OTP
- [ ] SMTP or SendGrid/SES integration
- [ ] HTML + plain text multipart
- [ ] Unsubscribe link for promotional emails

## Dependencies

- BE-060, BE-016

## Definition of Done

- [ ] Integration test with mail stub (GreenMail or similar)
