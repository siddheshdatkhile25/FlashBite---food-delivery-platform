---
name: build-production-app
description: >-
  Guides design and implementation of production-scale applications—architecture,
  NFRs, security, observability, testing, incremental delivery, and service
  boundaries. Use when building greenfield apps, scaling MVPs to production,
  microservices, event-driven systems, or when the user asks for enterprise-grade,
  production-ready, or system-design quality code.
---

# Build Production-Scale Applications

Apply this skill when implementing features, scaffolding services, or making architecture decisions. Default to **ship incrementally** with production discipline from day one—not a throwaway prototype.

## Core principles

1. **Requirements before code** — Confirm personas, critical paths, and P0 vs P1 scope.
2. **Smallest correct scope** — One vertical slice end-to-end beats many half-built layers.
3. **Explicit tradeoffs** — Document sync vs async, SQL vs NoSQL, consistency vs availability per use case.
4. **Fail safe** — Idempotency, timeouts, circuit breakers, and compensating actions for money and orders.
5. **Observable by default** — Structured logs, metrics, health checks, and trace IDs on every request.

## Phase 0: Discover (do not skip)

Read existing docs (`PRD.md`, `PROJECT_PLAN.md`, `README.md`, `docs/`) if present.

Extract and confirm with the user only when ambiguous:

| Item | Capture |
|------|---------|
| Product | Name, MVP scope, out-of-scope |
| Users / roles | RBAC matrix (who can call what) |
| Critical paths | 1–3 flows that must never break (e.g. place order → pay → deliver) |
| NFR targets | Latency P95, uptime, throughput, data retention |
| Stack | Languages, frameworks, infra (Docker Compose vs K8s) |
| Compliance | PCI, PII, GDPR export/delete |

Output a **one-paragraph architecture summary** before writing code.

## Phase 1: Architecture blueprint

Produce a short blueprint (markdown or mermaid) covering:

```
[Clients] → [API Gateway: auth, rate limit, routing]
         → [Services: bounded contexts]
         → [Data: polyglot per access pattern]
         → [Kafka/event bus for async side effects]
```

**Service boundaries** — One bounded context per service/module:

- Identity & profiles
- Core domain (orders, inventory, bookings, etc.)
- Payments / billing (isolated, audit-heavy)
- Notifications (consumers only, no blocking user path)
- Search / read models (CQRS projection if needed)
- Analytics (async, never on critical path)

**Start strategy** (default unless user says otherwise):

| Stage | Shape | When |
|-------|--------|------|
| MVP early | Modular monolith (Maven/npm workspaces, clear module folders) | Sprints 1–6 |
| Scale | Extract highest-churn services first (orders, real-time, payments) | After traffic or team pain |

For detailed patterns (Saga, CAP, storage mapping), see [reference.md](reference.md).

## Phase 2: Repository & foundation

Scaffold only what the current sprint needs:

```
backend/     # services or modules + shared common lib
frontend/    # role-based apps or routes + shared components
infrastructure/  # docker, migrations, monitoring configs
docs/        # ARCHITECTURE.md synced with code
```

**Foundation checklist:**

- [ ] `.gitignore` excludes secrets, `node_modules`, `target/`
- [ ] `docker-compose.yml` for local Postgres/Redis/Kafka/etc. as needed
- [ ] Env via `.env.example` — never commit real secrets
- [ ] API version prefix (`/api/v1/`)
- [ ] Global error shape: `{ code, message, traceId, details? }`
- [ ] Health: `/actuator/health` or `/health` per service
- [ ] Migrations: Flyway/Liquibase or Prisma migrate — no manual prod DDL

## Phase 3: Implement by vertical slice

For each feature, deliver in order:

1. **Data model** — Entities, indexes, optimistic locking on contested rows (`version` column).
2. **API contract** — OpenAPI or typed DTOs; validate at boundary.
3. **Domain logic** — Service layer; no business rules in controllers.
4. **Integration** — DB, cache, message publish with idempotency keys.
5. **AuthZ** — Enforce RBAC at gateway and service (defense in depth).
6. **Tests** — Unit for rules; integration for DB/API; contract tests between services.
7. **Observability** — Log `traceId`, `userId`, `orderId`; metric counters for success/failure.

**Sync vs async decision:**

| Need | Use |
|------|-----|
| User waiting for answer | Sync REST/gRPC |
| Side effects, notifications, analytics | Async events (Kafka/SQS) |
| High-frequency telemetry | Async or dedicated stream; never block HTTP |

**Payment / money paths:** Strong consistency, saga with compensation, audit log, never store raw card data.

## Phase 4: Production hardening

Before calling a milestone "production-ready":

### Security
- [ ] JWT/OAuth2 with short-lived access + refresh rotation
- [ ] Input validation server-side; parameterized queries
- [ ] Rate limits at gateway (per user + per IP)
- [ ] Secrets in vault/env — not in git
- [ ] CORS and security headers configured

### Reliability
- [ ] Timeouts on all outbound HTTP (2–5s default)
- [ ] Circuit breaker (e.g. Resilience4j) on external deps
- [ ] Retries only on idempotent ops with backoff
- [ ] DLQ for failed Kafka consumers; alert after N failures
- [ ] Graceful shutdown (drain connections)

### Performance
- [ ] DB indexes on filter/sort columns; explain slow queries
- [ ] Cache hot reads (TTL documented; stale OK or not)
- [ ] Pagination on all list endpoints (cursor preferred)
- [ ] P95 latency measured, not guessed

### Operations
- [ ] Structured JSON logs
- [ ] Prometheus metrics + Grafana dashboards (or equivalent)
- [ ] Distributed tracing (trace ID propagation)
- [ ] Runbooks for deploy, rollback, incident

## Phase 5: Definition of done (per PR)

```
- [ ] Acceptance criteria from PRD/ticket met
- [ ] Unit tests for domain logic (>80% on critical modules)
- [ ] Integration test for happy path + one failure path
- [ ] No secrets in diff
- [ ] API/docs updated if contract changed
- [ ] Migration reversible or forward-only with plan
- [ ] Load impact considered (N+1 queries, unbounded lists)
```

## Anti-patterns (reject unless user explicitly overrides)

- Big-bang microservices before first vertical slice works
- Shared database tables across services without bounded context
- Distributed 2PC for cross-service transactions
- Polling every second when SSE/WebSocket fits
- Caching payment or auth tokens incorrectly
- "We'll add tests later" on payment/order flows
- Generic `catch (Exception e)` with no rethrow or metric

## Response format when building

When executing a build task, structure replies as:

1. **Goal** — What slice ships
2. **Tradeoffs** — Key decisions (1–3 bullets)
3. **Changes** — Files/modules touched
4. **Verify** — Commands to test (`mvn test`, `pnpm test`, `docker compose up`)
5. **Next** — Next vertical slice or hardening item

## Additional resources

- Patterns, NFR checklist, event catalog template: [reference.md](reference.md)
- FlashBite-specific stack and sprints: [../../../PROJECT_PLAN.md](../../../PROJECT_PLAN.md), [../../../PRD.md](../../../PRD.md)
