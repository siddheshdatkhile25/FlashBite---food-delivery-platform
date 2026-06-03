# FlashBite

**AI-ready real-time food delivery and logistics platform**

FlashBite connects customers, restaurants, and delivery drivers through a single platform—from discovery and checkout to live tracking and post-delivery analytics. The architecture is built for production-scale microservices today and AI features (ETA prediction, recommendations, demand forecasting, and more) in later phases.

| | |
|---|---|
| **Product** | FlashBite MVP (v1.0) |
| **Backend** | Java 17, Spring Boot 3, Spring Cloud |
| **Frontend** | React 18 |
| **Status** | Early scaffold — services and UI in progress |

---

## What it does

### For customers
Search nearby restaurants, build a cart, pay, and track delivery in real time with sub‑5s location updates.

### For restaurants
Manage menus and availability, receive orders within seconds, and update order status through preparation and pickup.

### For drivers
Go online/offline, accept assignments, stream GPS updates, and view earnings.

### For operators
Monitor live metrics, approve partners, resolve disputes, and manage promotions.

---

## Architecture at a glance

The system starts as a **modular monolith** (Maven modules, one deployable) and evolves into independently scalable services. Order flow uses **Kafka** for async events and the **Saga pattern** for distributed transactions (payments, refunds, cancellations).

```
React SPA (customer · restaurant · driver · admin)
        │
   API Gateway (rate limiting, routing)
        │
   ┌────┴────┬─────────┬──────────┬──────────┐
 User    Order    Search   Payment   Delivery
   │        │        │         │          │
 Postgres  Postgres  Elastic   Postgres  MongoDB + Redis
                    Search
                        │
                     Kafka → Notification, Analytics
```

**Polyglot persistence**

| Data | Store | Why |
|------|-------|-----|
| Users, orders, payments | PostgreSQL | ACID, relational integrity |
| Driver GPS trail | MongoDB | High write rate, geo indexes, TTL |
| Sessions, cache, OTP | Redis | Low latency, ephemeral |
| Restaurant/menu search | Elasticsearch | Full-text and geo queries |
| Domain events | Kafka | Decoupling, replay, async workflows |

Real-time updates: **WebSockets** for drivers, **SSE** for customer order tracking.

Full design notes: [PROJECT_PLAN.md](./PROJECT_PLAN.md) · [PRD.md](./PRD.md) · [docs/ARCHITECTURE.md](./docs/ARCHITECTURE.md)

---

## Tech stack

| Layer | Technologies |
|-------|----------------|
| Backend | Spring Boot 3, Spring Cloud Gateway, Spring Security (JWT + OAuth2) |
| Frontend | React 18, Redux Toolkit, React Query, Leaflet/Mapbox, **pnpm** |
| Messaging | Apache Kafka |
| Databases | PostgreSQL, MongoDB, Redis, Elasticsearch |
| Observability | Prometheus, Grafana, ELK (target) |
| Local dev | Docker Compose |
| CI/CD | GitHub Actions (planned) |
| AI (post-MVP) | Python FastAPI microservice |

---

## Repository layout

```
├── backend/                 # Maven multi-module Spring Boot services
│   ├── api-gateway/
│   ├── common/              # Shared DTOs, events, security
│   ├── user-service/
│   ├── restaurant-service/
│   ├── order-service/
│   ├── payment-service/
│   ├── delivery-service/
│   ├── notification-service/
│   ├── search-service/
│   └── analytics-service/
├── frontend/                # React SPA — four role-based apps
│   └── src/apps/{customer,restaurant,driver,admin}/
├── ai-service/              # Python ML service (future phases)
├── infrastructure/          # Docker, monitoring, nginx configs
├── scripts/                 # Dev and ops helpers
├── docs/                    # Architecture and supplementary docs
├── docker-compose.yml       # Local Postgres, MongoDB, Redis, ES, Kafka
├── PRD.md                   # Product requirements
└── PROJECT_PLAN.md          # System design and incremental build plan
```

---

## Prerequisites

- **Java 17+** and **Maven 3.9+**
- **Node.js 18+** and **pnpm 9+** (frontend; enable via `corepack enable`)
- **Docker** and Docker Compose (local infrastructure)
- **Python 3.11+** (optional, for `ai-service` later)

---

## Getting started

### 1. Start infrastructure

```bash
docker compose up -d
```

This brings up:

| Service | Port |
|---------|------|
| PostgreSQL | 5432 |
| MongoDB | 27017 |
| Redis | 6379 |
| Elasticsearch | 9200 |
| Kafka | 9092 |

Default Postgres credentials: `flashbite` / `flashbite` (dev only).

### 2. Backend

```bash
cd backend
mvn clean install
```

Individual services will expose Spring Actuator health endpoints once implemented. Wire application configs to the Docker Compose hosts above.

### 3. Frontend

```bash
corepack enable   # once per machine — activates the pnpm version from package.json
cd frontend
pnpm install
pnpm dev
```

The frontend is organized by persona under `src/apps/`—customer, restaurant, driver, and admin—with shared components for maps, order tracking, and analytics.

---

## Incremental build plan

Development follows the sprint plan in [PROJECT_PLAN.md](./PROJECT_PLAN.md):

| Phase | Focus |
|-------|--------|
| Sprints 1–2 | User auth, restaurant/menu CRUD, PostgreSQL, basic React |
| Sprints 3–4 | Order flow, payment stub, order tracking UI |
| Sprints 5–6 | Kafka events, notification service |
| Sprints 7–8 | Delivery service, WebSocket + live map |
| Sprints 9–10 | Elasticsearch search, Redis caching |
| Sprints 11–12 | Service extraction, API Gateway, full Compose stack |
| Sprints 13–14 | Admin dashboard and analytics |
| Sprint 15+ | AI features (ETA prediction first) |

---

## AI roadmap (post-MVP)

Data pipelines are designed from day one. Planned capabilities include ETA prediction, smart recommendations, demand forecasting, dynamic pricing, an LLM support chatbot, fraud detection, and route optimization. Details and phase timelines are in [PRD.md](./PRD.md) §9.

The Java platform calls a **Python FastAPI** AI service over REST so ML stays in the ecosystem best suited for it.

---

## Documentation

| Document | Contents |
|----------|----------|
| [PRD.md](./PRD.md) | Functional requirements, APIs, events, NFRs, release plan |
| [PROJECT_PLAN.md](./PROJECT_PLAN.md) | Tradeoffs, schema highlights, frontend structure, sprints |
| [docs/ARCHITECTURE.md](./docs/ARCHITECTURE.md) | Module responsibilities and event catalog summary |

---

## License

Not specified yet. Add a `LICENSE` file before public distribution.
