# FlashBite Backend Ticket Tracker

This tracker shows which backend tickets are done and which are still pending based on the current repository snapshot as of July 21, 2026.

## Status Rule

- `Done`: ticket scope is implemented in the repo and supported by code/tests
- `Pending`: not started, partially scaffolded, or not yet verified against the ticket acceptance criteria

## Summary

| Status | Count |
|--------|-------|
| Done | 5 |
| Pending | 52 |

## Done

| ID | Title | Notes |
|----|-------|-------|
| [BE-001](./BE-001-common-shared-module.md) | Common shared module (DTOs, events, errors) | Shared enums, event envelope, error handling, security helpers, trace filter, and unit tests are present in `backend/common`. |
| [BE-002](./BE-002-api-gateway.md) | API Gateway (routing, rate limiting, JWT) | Gateway routes are configured, protected APIs enforce JWT, trace IDs are forwarded, rate limiting returns `429`, and the module now has integration coverage. |
| [BE-003](./BE-003-database-migrations-foundation.md) | Database migrations & multi-service DB setup | Flyway schemas configured per bounded context, `docker-compose` auto-initializes DBs on Postgres mount, integration tests are backed by `Testcontainers`. |
| [BE-010](./BE-010-user-registration-login.md) | User registration & email/password login | Completed and fully tested. |
| [BE-011](./BE-011-jwt-refresh-tokens.md) | JWT access & refresh token management | Stored in Redis, refresh token reuse detection and rotation fully implemented. |

## Pending

| ID | Title | Notes |
|----|-------|-------|
| [BE-012](./BE-012-oauth2-social-login.md) | OAuth2 social login (Google, Facebook) | Pending |
| [BE-013](./BE-013-rbac.md) | Role-based access control | Pending |
| [BE-014](./BE-014-profile-management.md) | Profile management | Pending |
| [BE-015](./BE-015-address-management.md) | Saved delivery addresses | Pending |
| [BE-016](./BE-016-password-reset-otp.md) | Password reset via email OTP | Pending |
| [BE-020](./BE-020-restaurant-registration-profile.md) | Restaurant registration & profile | Pending |
| [BE-021](./BE-021-menu-crud.md) | Menu CRUD (categories, items, prices) | Pending |
| [BE-022](./BE-022-operating-hours.md) | Operating hours & holiday schedule | Pending |
| [BE-023](./BE-023-item-availability.md) | Toggle item availability (out-of-stock) | Pending |
| [BE-024](./BE-024-incoming-orders.md) | View incoming orders (live) | Pending |
| [BE-025](./BE-025-restaurant-order-status.md) | Update order status (confirm → ready) | Pending |
| [BE-026](./BE-026-restaurant-analytics.md) | Revenue & order analytics | Pending |
| [BE-030](./BE-030-server-side-cart.md) | Server-side cart management | Pending |
| [BE-031](./BE-031-cart-validation.md) | Cart validation before checkout | Pending |
| [BE-032](./BE-032-checkout-place-order.md) | Checkout & place order | Pending |
| [BE-033](./BE-033-order-lifecycle.md) | Order lifecycle state machine | Pending |
| [BE-034](./BE-034-order-cancellation-saga.md) | Order cancellation & saga compensation | Pending |
| [BE-035](./BE-035-order-history-reorder.md) | Order history & reorder | Pending |
| [BE-036](./BE-036-sse-order-tracking.md) | Real-time order status via SSE | Pending |
| [BE-040](./BE-040-payment-gateway-integration.md) | Payment gateway integration (Stripe/Razorpay) | Pending |
| [BE-041](./BE-041-order-total-calculation.md) | Order total calculation (tax, fees, discounts) | Pending |
| [BE-042](./BE-042-refund-processing.md) | Refund processing (full/partial) | Pending |
| [BE-043](./BE-043-payment-failure-retry.md) | Payment failure handling & retry | Pending |
| [BE-044](./BE-044-transaction-history.md) | Transaction history export | Pending |
| [BE-045](./BE-045-restaurant-payout.md) | Restaurant payout & settlement | Pending |
| [BE-050](./BE-050-driver-availability.md) | Driver online/offline toggle | Pending |
| [BE-051](./BE-051-driver-assignment.md) | Automatic driver assignment | Pending |
| [BE-052](./BE-052-assignment-accept-reject.md) | Driver accept/reject with timeout | Pending |
| [BE-053](./BE-053-gps-location-tracking.md) | Real-time GPS location trail (MongoDB) | Pending |
| [BE-054](./BE-054-delivery-status-updates.md) | Delivery status updates | Pending |
| [BE-055](./BE-055-proof-of-delivery.md) | Proof of delivery | Pending |
| [BE-056](./BE-056-driver-earnings.md) | Driver earnings dashboard | Pending |
| [BE-060](./BE-060-kafka-event-infrastructure.md) | Kafka producers, consumers & DLQ | Pending |
| [BE-061](./BE-061-push-notifications.md) | Push notifications | Pending |
| [BE-062](./BE-062-sms-notifications.md) | SMS notifications (Twilio/SNS) | Pending |
| [BE-063](./BE-063-email-notifications.md) | Email notifications | Pending |
| [BE-064](./BE-064-in-app-notification-center.md) | In-app notification center | Pending |
| [BE-065](./BE-065-notification-preferences.md) | Notification channel preferences | Pending |
| [BE-070](./BE-070-elasticsearch-indexing.md) | Elasticsearch indexing pipeline | Pending |
| [BE-071](./BE-071-fulltext-geo-search.md) | Full-text & geo-based search | Pending |
| [BE-072](./BE-072-search-filters-sorting.md) | Filters & sorting | Pending |
| [BE-073](./BE-073-recently-viewed-reorder.md) | Recently viewed & reorder support | Pending |
| [BE-080](./BE-080-ratings-reviews.md) | Ratings & reviews | Pending |
| [BE-081](./BE-081-coupons-promotions.md) | Coupons & promotions | Pending |
| [BE-082](./BE-082-admin-dashboard.md) | Admin live metrics dashboard | Pending |
| [BE-083](./BE-083-admin-user-management.md) | Admin user/restaurant/driver management | Pending |
| [BE-084](./BE-084-admin-disputes-refunds.md) | Dispute resolution & manual refunds | Pending |
| [BE-085](./BE-085-system-health-monitoring.md) | System health monitoring dashboard | Pending |
| [BE-090](./BE-090-observability.md) | Observability (logs, metrics, tracing) | Pending |
| [BE-091](./BE-091-resilience-security.md) | Circuit breakers, timeouts, DLQ alerts | Pending |
| [BE-092](./BE-092-integration-contract-tests.md) | Integration & contract tests | Pending |
| [BE-093](./BE-093-load-testing.md) | Load testing critical paths | Pending |

## Notes

- No ticket is currently marked as `In Progress` in this tracker.
- Partial scaffolding does not count as `Done` until the ticket acceptance criteria are satisfied.
- If you want, this tracker can be updated later to include `In Progress`, owner, and last-updated columns.
