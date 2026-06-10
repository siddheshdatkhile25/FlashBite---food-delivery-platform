# FlashBite — Backend Tickets

Implementation tickets for the Java/Spring Boot backend, derived from [PRD.md](../../../PRD.md), [PROJECT_PLAN.md](../../../PROJECT_PLAN.md), and [docs/ARCHITECTURE.md](../../ARCHITECTURE.md).

## Status legend

| Status | Meaning |
|--------|---------|
| **Todo** | Not started |
| **In Progress** | Active development |
| **Done** | Merged and verified |
| **Blocked** | Waiting on dependency |

## Sprint map

| Sprint | Focus | Tickets |
|--------|-------|---------|
| 1–2 | Foundation, auth, restaurant/menu | BE-001 – BE-026 |
| 3–4 | Order flow, payment stub | BE-030 – BE-045 |
| 5–6 | Kafka, notifications | BE-060 – BE-065 |
| 7–8 | Delivery, real-time tracking | BE-050 – BE-056 |
| 9–10 | Search, caching | BE-070 – BE-073 |
| 11–12 | API Gateway, observability, hardening | BE-002, BE-090 – BE-093 |
| 13–14 | Admin, analytics, ratings, coupons | BE-080 – BE-085 |

## Ticket index

### Foundation & platform

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-001](BE-001-common-shared-module.md) | Common shared module (DTOs, events, errors) | `common` | P0 |
| [BE-002](BE-002-api-gateway.md) | API Gateway (routing, rate limiting, JWT) | `api-gateway` | P0 |
| [BE-003](BE-003-database-migrations-foundation.md) | Database migrations & multi-service DB setup | `infrastructure` | P0 |

### User service

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-010](BE-010-user-registration-login.md) | User registration & email/password login | `user-service` | P0 |
| [BE-011](BE-011-jwt-refresh-tokens.md) | JWT access & refresh token management | `user-service` | P0 |
| [BE-012](BE-012-oauth2-social-login.md) | OAuth2 social login (Google, Facebook) | `user-service` | P0 |
| [BE-013](BE-013-rbac.md) | Role-based access control | `user-service` | P0 |
| [BE-014](BE-014-profile-management.md) | Profile management | `user-service` | P0 |
| [BE-015](BE-015-address-management.md) | Saved delivery addresses | `user-service` | P0 |
| [BE-016](BE-016-password-reset-otp.md) | Password reset via email OTP | `user-service` | P0 |

### Restaurant service

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-020](BE-020-restaurant-registration-profile.md) | Restaurant registration & profile | `restaurant-service` | P0 |
| [BE-021](BE-021-menu-crud.md) | Menu CRUD (categories, items, prices) | `restaurant-service` | P0 |
| [BE-022](BE-022-operating-hours.md) | Operating hours & holiday schedule | `restaurant-service` | P0 |
| [BE-023](BE-023-item-availability.md) | Toggle item availability (out-of-stock) | `restaurant-service` | P0 |
| [BE-024](BE-024-incoming-orders.md) | View incoming orders (live) | `restaurant-service` | P0 |
| [BE-025](BE-025-restaurant-order-status.md) | Update order status (confirm → ready) | `restaurant-service` | P0 |
| [BE-026](BE-026-restaurant-analytics.md) | Revenue & order analytics | `restaurant-service` | P0 |

### Order service

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-030](BE-030-server-side-cart.md) | Server-side cart management | `order-service` | P0 |
| [BE-031](BE-031-cart-validation.md) | Cart validation before checkout | `order-service` | P0 |
| [BE-032](BE-032-checkout-place-order.md) | Checkout & place order | `order-service` | P0 |
| [BE-033](BE-033-order-lifecycle.md) | Order lifecycle state machine | `order-service` | P0 |
| [BE-034](BE-034-order-cancellation-saga.md) | Order cancellation & saga compensation | `order-service` | P0 |
| [BE-035](BE-035-order-history-reorder.md) | Order history & reorder | `order-service` | P0 |
| [BE-036](BE-036-sse-order-tracking.md) | Real-time order status via SSE | `order-service` | P0 |

### Payment service

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-040](BE-040-payment-gateway-integration.md) | Payment gateway integration (Stripe/Razorpay) | `payment-service` | P0 |
| [BE-041](BE-041-order-total-calculation.md) | Order total calculation (tax, fees, discounts) | `payment-service` | P0 |
| [BE-042](BE-042-refund-processing.md) | Refund processing (full/partial) | `payment-service` | P0 |
| [BE-043](BE-043-payment-failure-retry.md) | Payment failure handling & retry | `payment-service` | P0 |
| [BE-044](BE-044-transaction-history.md) | Transaction history export | `payment-service` | P0 |
| [BE-045](BE-045-restaurant-payout.md) | Restaurant payout & settlement | `payment-service` | P1 |

### Delivery service

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-050](BE-050-driver-availability.md) | Driver online/offline toggle | `delivery-service` | P0 |
| [BE-051](BE-051-driver-assignment.md) | Automatic driver assignment | `delivery-service` | P0 |
| [BE-052](BE-052-assignment-accept-reject.md) | Driver accept/reject with timeout | `delivery-service` | P0 |
| [BE-053](BE-053-gps-location-tracking.md) | Real-time GPS location trail (MongoDB) | `delivery-service` | P0 |
| [BE-054](BE-054-delivery-status-updates.md) | Delivery status updates | `delivery-service` | P0 |
| [BE-055](BE-055-proof-of-delivery.md) | Proof of delivery | `delivery-service` | P0 |
| [BE-056](BE-056-driver-earnings.md) | Driver earnings dashboard | `delivery-service` | P0 |

### Notification service

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-060](BE-060-kafka-event-infrastructure.md) | Kafka producers, consumers & DLQ | `common` + all | P0 |
| [BE-061](BE-061-push-notifications.md) | Push notifications | `notification-service` | P0 |
| [BE-062](BE-062-sms-notifications.md) | SMS notifications (Twilio/SNS) | `notification-service` | P0 |
| [BE-063](BE-063-email-notifications.md) | Email notifications | `notification-service` | P0 |
| [BE-064](BE-064-in-app-notification-center.md) | In-app notification center | `notification-service` | P0 |
| [BE-065](BE-065-notification-preferences.md) | Notification channel preferences | `notification-service` | P0 |

### Search service

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-070](BE-070-elasticsearch-indexing.md) | Elasticsearch indexing pipeline | `search-service` | P0 |
| [BE-071](BE-071-fulltext-geo-search.md) | Full-text & geo-based search | `search-service` | P0 |
| [BE-072](BE-072-search-filters-sorting.md) | Filters & sorting | `search-service` | P0 |
| [BE-073](BE-073-recently-viewed-reorder.md) | Recently viewed & reorder support | `search-service` + `order-service` | P0 |

### Ratings, promotions & admin

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-080](BE-080-ratings-reviews.md) | Ratings & reviews | `order-service` + `restaurant-service` | P1 |
| [BE-081](BE-081-coupons-promotions.md) | Coupons & promotions | `order-service` + `payment-service` | P1 |
| [BE-082](BE-082-admin-dashboard.md) | Admin live metrics dashboard | `analytics-service` | P1 |
| [BE-083](BE-083-admin-user-management.md) | Admin user/restaurant/driver management | `user-service` + `analytics-service` | P1 |
| [BE-084](BE-084-admin-disputes-refunds.md) | Dispute resolution & manual refunds | `payment-service` + `order-service` | P1 |
| [BE-085](BE-085-system-health-monitoring.md) | System health monitoring dashboard | `analytics-service` | P1 |

### Production hardening

| ID | Title | Module | Priority |
|----|-------|--------|----------|
| [BE-090](BE-090-observability.md) | Observability (logs, metrics, tracing) | all | P0 |
| [BE-091](BE-091-resilience-security.md) | Circuit breakers, timeouts, DLQ alerts | all | P0 |
| [BE-092](BE-092-integration-contract-tests.md) | Integration & contract tests | all | P0 |
| [BE-093](BE-093-load-testing.md) | Load testing critical paths | all | P1 |

## Dependency graph (critical path)

```
BE-001 → BE-003 → BE-010 → BE-011 → BE-013
                → BE-020 → BE-021
BE-010 + BE-020 + BE-021 → BE-030 → BE-031 → BE-032
BE-032 → BE-040 → BE-060 → BE-061/062/063
BE-025 → BE-051 → BE-052 → BE-053 → BE-054
BE-020 + BE-021 → BE-070 → BE-071
BE-002 (after core services runnable)
```

## Ticket template

Each ticket follows this structure:

- Metadata (ID, module, sprint, priority, PRD refs)
- Summary & scope
- Acceptance criteria (checkboxes)
- API endpoints & data model
- Kafka events (if applicable)
- Dependencies
- Definition of done
