# Production-Scale Patterns Reference

Read when implementing distributed features, choosing storage, or designing event flows.

## CAP and consistency choices

| Scenario | Prefer | Pattern |
|----------|--------|---------|
| Payment deduction | Consistency (CP) | Saga + compensation; idempotent payment API |
| Catalog / "open now" | Availability (AP) | Cache TTL 30–60s; stale OK |
| Order status for UI | Eventual | Kafka events + SSE/WebSocket to client |
| Inventory decrement | Strong | Single DB transaction in owning service |

## Saga choreography (order-style flows)

```
Order Created → Payment Authorized → Domain Confirmed → Side Effect Assigned
     │ fail anywhere
     └── Compensating events (refund, cancel, release inventory)
```

Rules:
- Every step idempotent (`Idempotency-Key` header or event `eventId`)
- Store saga state (`PENDING`, `COMPLETED`, `COMPENSATING`)
- Never assume single global transaction across services

## Polyglot persistence

| Pattern | Store | Example |
|---------|-------|---------|
| Transactional core | PostgreSQL | users, orders, payments |
| High-write telemetry | MongoDB + TTL | GPS trail, activity logs |
| Session / cache | Redis | OTP, cart cache, rate limit counters |
| Search / geo | Elasticsearch | restaurant/menu search |
| Events | Kafka | `domain.action` topics, replication ≥ 3 in prod |

## API design

- **REST** for service-to-service and public APIs; version in path `/api/v1/`
- **BFF** optional for frontend aggregation (menu + reviews + promos in one call)
- **Idempotency**: `POST` that creates money or orders accepts `Idempotency-Key`
- **Errors**: HTTP status + stable `code` enum + `traceId`
- **Lists**: `limit` + `cursor`; max page size 50–100

## Real-time transport

| Use case | Choice |
|----------|--------|
| Server → client status stream | SSE (simpler LB) |
| Bidirectional (driver chat, live commands) | WebSocket + Redis pub/sub if multi-instance |
| Admin dashboards | Poll 30s or SSE |

## Event topic naming

```
<aggregate>.<past-tense-verb>
order.placed
payment.succeeded
payment.failed
order.cancelled
```

Payload minimum: `eventId`, `timestamp`, `aggregateId`, `version`, `payload`.

Consumers must handle **at-least-once** delivery (dedupe by `eventId`).

## Resilience defaults

```yaml
# Conceptual — adapt to Spring Resilience4j, etc.
http_client:
  connect_timeout: 2s
  read_timeout: 5s
circuit_breaker:
  failure_rate_threshold: 50%
  wait_duration_in_open_state: 30s
retry:
  max_attempts: 3
  only_on: [timeout, 503]
  idempotent: true
```

## Security checklist (extended)

- RBAC at gateway **and** service layer
- PII encrypted at rest; TLS 1.2+ in transit
- Audit log: admin actions, refunds, role changes
- OWASP: CSRF for cookie sessions; sanitize outputs
- Dependency scanning in CI (Dependabot/Snyk)

## Observability fields (structured log)

```json
{
  "timestamp": "ISO-8601",
  "level": "INFO",
  "service": "order-service",
  "traceId": "uuid",
  "spanId": "uuid",
  "userId": "uuid",
  "message": "Order placed",
  "orderId": "uuid",
  "durationMs": 42
}
```

Metrics to expose: `http_requests_total`, `http_request_duration_seconds`, `kafka_consumer_lag`, business counters (`orders_placed_total`).

## NFR targets (template — fill per project)

| Metric | Starter target |
|--------|----------------|
| API P95 latency | < 300ms |
| API P99 | < 1s |
| Uptime | 99.9% |
| Search P95 | < 200ms |
| Event processing lag | < 5s |
| RPO (financial) | < 1 min |
| RTO | < 15 min |

## Testing pyramid

| Layer | Scope |
|-------|--------|
| Unit | Domain rules, pure functions, mappers |
| Integration | Repository + DB (Testcontainers) |
| Contract | Pact between producer/consumer |
| E2E | Critical paths only (order, pay, cancel) |
| Load | k6/Gatling at 10× expected peak before launch |

## Incremental delivery template

| Sprint | Deliverable | Production gate |
|--------|-------------|-------------------|
| 1–2 | Auth + core CRUD + one UI flow | Migrations, basic tests |
| 3–4 | Primary transaction flow + stub external deps | Integration tests |
| 5–6 | Event bus + async notifications | DLQ, consumer idempotency |
| 7–8 | Real-time + second data store | Load test critical path |
| 9–10 | Search + cache | Index strategy, cache invalidation |
| 11+ | Gateway, extract services, monitoring | Dashboards, alerts, runbooks |

## AI / ML integration (when applicable)

- Keep inference in **Python FastAPI** (or managed API); core app stays typed/monolith or Java/Go
- Feature store (Redis) for low-latency features at inference
- Never block checkout on model latency — fallback to rule-based ETA
- Log predictions + outcomes for model retraining pipelines
