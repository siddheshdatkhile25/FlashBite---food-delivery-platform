# BE-002: API Gateway

| Field | Value |
|-------|-------|
| **ID** | BE-002 |
| **Module** | `api-gateway` |
| **Sprint** | 11–12 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | §4.4 Security (rate limiting, RBAC at gateway) |

## Summary

Spring Cloud Gateway as the single entry point: route `/api/v1/**` to backend services, validate JWT, enforce rate limits, and propagate trace IDs.

## Scope

- Route definitions per service (user, restaurant, order, payment, delivery, search, analytics)
- JWT validation filter (reject expired/invalid tokens on protected routes)
- Public routes whitelist: `/api/v1/auth/**`, `/api/v1/health`, actuator
- Token bucket rate limiting per user + sliding window per IP
- CORS configuration for React SPA origins
- Request/response logging with `traceId`

## Acceptance Criteria

- [ ] All PRD §7 endpoints reachable through gateway on single port (e.g. 8080)
- [ ] Unauthenticated requests to protected routes return 401
- [ ] Rate limit returns 429 with `Retry-After` header
- [ ] `traceId` forwarded to downstream services via `X-Trace-Id`
- [ ] Gateway health check at `/actuator/health`

## API Routes (examples)

| Path prefix | Target service |
|-------------|----------------|
| `/api/v1/auth/**` | user-service |
| `/api/v1/restaurants/**` | search-service / restaurant-service |
| `/api/v1/cart/**`, `/api/v1/orders/**` | order-service |
| `/api/v1/payments/**` | payment-service |
| `/api/v1/driver/**` | delivery-service |
| `/api/v1/admin/**` | analytics-service + user-service |

## Dependencies

- BE-001, BE-011 (JWT format)
- Core services runnable on known ports

## Definition of Done

- [ ] Integration test: auth route public, orders route protected
- [ ] Rate limit tested with burst traffic
- [ ] `docker-compose.yml` exposes gateway as primary backend URL
