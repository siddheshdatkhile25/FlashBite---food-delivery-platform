# BE-003: Database Migrations & Foundation

| Field | Value |
|-------|-------|
| **ID** | BE-003 |
| **Module** | `infrastructure` / all PostgreSQL services |
| **Sprint** | 1 |
| **Priority** | P0 |
| **Status** | Done |
| **PRD** | §6 Data Model |

## Summary

Set up Flyway migrations, PostgreSQL schemas per bounded context, and Spring Data JPA configuration for transactional services.

## Scope

- Flyway in `user-service`, `restaurant-service`, `order-service`, `payment-service`, `delivery-service` (assignment tables)
- Schema naming: `user`, `restaurant`, `order`, `payment`, `delivery` (or separate DBs per service in Compose)
- Base entity audit fields: `created_at`, `updated_at`
- Optimistic locking `version` column on `orders` and `payments`
- Indexes from PROJECT_PLAN schema highlights
- Testcontainers setup for integration tests

## Acceptance Criteria

- [x] `docker compose up -d` Postgres connects from all services
- [x] Flyway migrations run on startup without manual DDL
- [x] `orders` table matches PROJECT_PLAN schema (UUID PK, status, JSONB address, version)
- [x] Migrations are versioned and idempotent on fresh DB
- [x] `.env.example` documents DB connection vars

## Data Model (initial)

- `users`, `addresses`, `refresh_tokens`
- `restaurants`, `menu_categories`, `menu_items`
- `orders`, `order_items`
- `payments`, `refunds`

## Dependencies

- BE-001

## Definition of Done

- [x] Integration test boots service with Testcontainers Postgres
- [x] No secrets in `application.yml` (env vars only)
