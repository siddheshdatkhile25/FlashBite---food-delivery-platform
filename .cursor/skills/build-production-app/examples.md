# Examples

## Example: User asks to "add checkout"

**Agent response shape:**

**Goal:** Customer can place order with validated cart and stub payment.

**Tradeoffs:**
- Sync `POST /api/v1/orders` for immediate confirmation
- Publish `order.placed` async for restaurant notification (stub consumer OK in sprint 3)
- PostgreSQL + optimistic lock on `orders.version`

**Changes:** `order-service` entity/repo/controller, Flyway `V2__orders.sql`, frontend checkout page, integration test.

**Verify:**
```bash
docker compose up -d postgres kafka
cd backend && mvn -pl order-service test
cd frontend && pnpm test
```

**Next:** Payment service + `payment.success` consumer.

---

## Example: Architecture summary (FlashBite-style)

FlashBite MVP: modular Spring Boot modules behind a gateway, React apps per role, Kafka for order side effects, PostgreSQL for money/orders, MongoDB for driver GPS, Elasticsearch for search. Sprint 1–2 delivers auth + restaurant CRUD; order saga starts sprint 3.

---

## Example: Rejecting a request

User: "Split into 12 microservices before we have one API working."

Agent: Recommend modular monolith first per build-production-app Phase 1; offer folder structure per bounded context and extraction plan after first vertical slice passes integration tests.
