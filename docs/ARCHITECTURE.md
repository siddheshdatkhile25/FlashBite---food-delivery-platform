# Architecture overview

Derived from [PRD.md](../PRD.md) and [PROJECT_PLAN.md](../PROJECT_PLAN.md).

## Backend modules

| Module | Responsibility |
|--------|----------------|
| `api-gateway` | Spring Cloud Gateway, rate limiting, routing |
| `common` | Shared DTOs, Kafka events, security utilities (see below) |
| `user-service` | Auth, profiles, RBAC, addresses |
| `restaurant-service` | Restaurants, menus, hours, order intake |
| `order-service` | Cart, checkout, order lifecycle, saga |
| `payment-service` | Payments, refunds, settlements |
| `delivery-service` | Driver matching, GPS trail (MongoDB) |
| `notification-service` | Push, SMS, email (Kafka consumers) |
| `search-service` | Elasticsearch indexing and queries |
| `analytics-service` | Dashboards, KPI consumers |

## Common module (`backend/common`)

Library JAR (no Spring Boot app) consumed by all services.

| Package | Contents |
|---------|----------|
| `api` | `ErrorResponse`, `ApiConstants` (`X-Trace-Id`, `Idempotency-Key`) |
| `domain` | `UserRole`, `OrderStatus`, `PaymentStatus`, `DeliveryStatus` |
| `events` | `DomainEvent<T>`, `EventTopics`, payload records |
| `exception` | `ErrorCode`, `FlashBiteException`, `GlobalExceptionHandler` |
| `security` | `JwtClaims`, `RoleGuard` |
| `util` | `IdempotencyKeyValidator` |
| `web` | `TraceIdFilter`, `MdcKeys` |

Error shape: `{ code, message, traceId, timestamp?, details? }`.

## Kafka topics (event catalog)

See PRD §8: `order.placed`, `payment.success`, `order.confirmed`, `order.ready`, `driver.assigned`, etc.

## Frontend apps

- `customer` — discovery, cart, checkout, tracking (SSE)
- `restaurant` — menu CRUD, incoming orders
- `driver` — assignments, GPS, earnings (mobile-first)
- `admin` — metrics, approvals, disputes

## Build path

Sprints 1–2: modular monolith + PostgreSQL + React basics → later extract services and add Kafka (see PROJECT_PLAN incremental table).

## Implementation tickets

Backend work is tracked in [docs/tickets/backend/](tickets/backend/README.md) (BE-001 through BE-093).
