# AI-Ready Real-Time Food Delivery & Logistics Platform

A production-grade system design project covering microservices, event-driven architecture, distributed systems tradeoffs, and an AI integration roadmap. Built with **Java (Spring Boot)** and **React**.

---

## Table of Contents

- [Core Features (MVP)](#core-features-mvp)
- [System Design & Architecture](#system-design--architecture)
- [Tech Stack](#tech-stack)
- [Key System Design Tradeoffs](#key-system-design-tradeoffs)
- [Database Schema Highlights](#database-schema-highlights)
- [AI Integration Roadmap](#ai-integration-roadmap-future-phases)
- [React Frontend Structure](#react-frontend-structure)
- [Incremental Build Plan](#how-to-build-this-incrementally)

---

## Core Features (MVP)


| Module                   | Description                                                                           |
| ------------------------ | ------------------------------------------------------------------------------------- |
| **User Service**         | Registration, login, profiles, roles (customer, restaurant, driver, admin)            |
| **Restaurant Service**   | Menu management, availability, hours, ratings                                         |
| **Order Service**        | Cart, checkout, order lifecycle (placed → confirmed → preparing → picked → delivered) |
| **Payment Service**      | Payment processing, wallets, refunds, invoicing                                       |
| **Delivery Service**     | Driver matching, real-time location tracking, route management                        |
| **Notification Service** | Push notifications, SMS, email, in-app alerts                                         |
| **Search & Discovery**   | Restaurant/menu search with filters, geo-based results                                |
| **Analytics Service**    | Order trends, revenue dashboards, operational metrics                                 |


---

## System Design & Architecture

### High-Level Architecture

```
                        ┌──────────────┐
                        │  React SPA   │
                        │  (Customer,  │
                        │  Restaurant, │
                        │  Driver,     │
                        │  Admin)      │
                        └──────┬───────┘
                               │
                        ┌──────▼───────┐
                        │  API Gateway │ (Spring Cloud Gateway)
                        │  + Rate Limit│
                        └──────┬───────┘
                               │
          ┌────────────┬───────┼────────┬──────────────┐
          ▼            ▼       ▼        ▼              ▼
    ┌──────────┐ ┌─────────┐ ┌──────┐ ┌────────┐ ┌──────────┐
    │  User    │ │  Order  │ │Search│ │Payment │ │ Delivery │
    │  Service │ │  Service│ │  Svc │ │Service │ │  Service │
    │(Spring)  │ │(Spring) │ │      │ │(Spring)│ │ (Spring) │
    └────┬─────┘ └────┬────┘ └──┬───┘ └───┬────┘ └────┬─────┘
         │            │         │         │            │
    ┌────▼───┐   ┌────▼───┐ ┌──▼────┐ ┌──▼────┐  ┌───▼──────┐
    │PostgreS│   │PostgreS│ │Elastic│ │PostgreS│  │  Redis +  │
    │   QL   │   │QL+Redis│ │Search │ │  QL   │  │  MongoDB  │
    └────────┘   └────────┘ └───────┘ └───────┘  └──────────┘
                        │
                  ┌─────▼──────┐
                  │   Kafka    │  (Event Bus)
                  └────────────┘
                        │
               ┌────────▼────────┐
               │  Notification   │
               │    Service      │
               └─────────────────┘
```

---

## Tech Stack


| Layer                 | Technology                                                                          |
| --------------------- | ----------------------------------------------------------------------------------- |
| **Backend**           | Java 17+, Spring Boot 3, Spring Cloud                                               |
| **Frontend**          | React 18, Redux Toolkit, React Query, Leaflet/Mapbox for maps                       |
| **API Gateway**       | Spring Cloud Gateway                                                                |
| **Service Discovery** | Eureka or Consul                                                                    |
| **Messaging**         | Apache Kafka                                                                        |
| **Databases**         | PostgreSQL (transactional), MongoDB (delivery tracking), Redis (caching + sessions) |
| **Search**            | Elasticsearch                                                                       |
| **Real-time**         | WebSockets (STOMP over SockJS) or SSE                                               |
| **Auth**              | Spring Security + JWT + OAuth2                                                      |
| **Containerization**  | Docker + Docker Compose (dev), Kubernetes (prod)                                    |
| **CI/CD**             | GitHub Actions                                                                      |
| **Monitoring**        | Prometheus + Grafana, ELK Stack                                                     |


---

## Key System Design Tradeoffs

### 1. Monolith vs Microservices


|                      | Monolith         | Microservices                            |
| -------------------- | ---------------- | ---------------------------------------- |
| **Complexity**       | Low              | High (service mesh, distributed tracing) |
| **Deployment**       | Simple           | Independent per service                  |
| **Scaling**          | Scale everything | Scale only what needs it                 |
| **Data consistency** | Easy (single DB) | Hard (distributed transactions)          |


> **Decision**: Start with a **modular monolith** (separate Maven modules sharing one deployable), then extract Order and Delivery services first since they have the highest independent scaling needs.

---

### 2. Synchronous (REST/gRPC) vs Asynchronous (Kafka Events)


| Use Case                  | Choice               | Why                                                      |
| ------------------------- | -------------------- | -------------------------------------------------------- |
| User places order         | **Sync (REST)**      | User needs immediate confirmation                        |
| Order → notify restaurant | **Async (Kafka)**    | Decouples; restaurant can be temporarily down            |
| Payment confirmation      | **Async (Kafka)**    | Eventual consistency is acceptable; retries are critical |
| Driver location updates   | **Async (Kafka/WS)** | High throughput, fire-and-forget                         |


> **Tradeoff**: Sync gives strong consistency but creates tight coupling. Async gives resilience but you must handle eventual consistency, idempotency, and dead-letter queues.

---

### 3. SQL (PostgreSQL) vs NoSQL (MongoDB)


| Data                                | Choice            | Why                                                         |
| ----------------------------------- | ----------------- | ----------------------------------------------------------- |
| Users, Orders, Payments             | **PostgreSQL**    | Strong consistency, ACID transactions, relational integrity |
| Driver GPS trail (lat/lng every 5s) | **MongoDB**       | High write throughput, schema flexibility, TTL indexes      |
| Session / Cache                     | **Redis**         | Sub-millisecond reads, TTL-based expiry                     |
| Menu/Restaurant search              | **Elasticsearch** | Full-text search, geo-queries, faceted filtering            |


> **Tradeoff**: PostgreSQL guarantees you won't lose money (payments). MongoDB handles the firehose of location data that would overwhelm a relational DB.

---

### 4. Consistency vs Availability (CAP Theorem in Practice)


| Scenario                     | Choice                   | Pattern                                             |
| ---------------------------- | ------------------------ | --------------------------------------------------- |
| Payment deduction            | **Consistency** (CP)     | Saga pattern with compensation                      |
| Showing "restaurant is open" | **Availability** (AP)    | Cache with 30s TTL; stale is acceptable             |
| Order status                 | **Eventual consistency** | Kafka event propagation; UI polls or uses WebSocket |


> **Tradeoff**: Use the **Saga pattern** (choreography via Kafka) for the order flow:
> `Order Created → Payment Deducted → Restaurant Confirmed → Driver Assigned`.
> If any step fails, compensating events roll back prior steps. This avoids distributed 2PC but requires careful idempotency design.

---

### 5. Push vs Pull for Real-Time Updates


|                   | WebSocket                              | SSE                     | Polling          |
| ----------------- | -------------------------------------- | ----------------------- | ---------------- |
| **Bidirectional** | Yes                                    | No                      | No               |
| **Complexity**    | High (connection mgmt)                 | Medium                  | Low              |
| **Scalability**   | Needs sticky sessions or Redis pub/sub | Easier to load-balance  | Simplest         |
| **Use here**      | Driver ↔ Server                        | Customer order tracking | Admin dashboards |


> **Tradeoff**: Use WebSockets for driver communication (bidirectional needed). Use SSE for customer-facing order tracking (simpler, auto-reconnect). Fall back to polling for admin dashboards where real-time isn't critical.

---

### 6. API Design: REST vs GraphQL


|                         | REST                | GraphQL                |
| ----------------------- | ------------------- | ---------------------- |
| **Simplicity**          | Straightforward     | Steeper learning curve |
| **Over/under-fetching** | Common problem      | Solved by design       |
| **Caching**             | Easy (HTTP caching) | Harder                 |
| **Best for**            | Service-to-service  | Frontend-to-backend    |


> **Tradeoff**: Use REST for inter-service communication (simpler, cacheable). Consider GraphQL (or BFF pattern) for the React frontend to reduce round trips on the restaurant detail page (menu + reviews + ratings in one call).

---

### 7. Rate Limiting & Circuit Breaking

- **Rate Limiting** (at API Gateway): Token bucket per user, sliding window per IP. Protects against abuse.
- **Circuit Breaker** (Resilience4j): If Payment Service is down, fail fast instead of cascading timeouts. Fallback: queue the payment for retry.

> **Tradeoff**: Aggressive rate limiting protects infrastructure but risks blocking legitimate burst traffic (e.g., flash sales). Use adaptive limits.

---

## Database Schema Highlights

### Order Service (PostgreSQL)

```sql
CREATE TABLE orders (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id     UUID NOT NULL,
    restaurant_id   UUID NOT NULL,
    driver_id       UUID,
    status          VARCHAR(20) NOT NULL DEFAULT 'PLACED',
    total_amount    DECIMAL(10,2) NOT NULL,
    delivery_address JSONB NOT NULL,
    placed_at       TIMESTAMP DEFAULT NOW(),
    delivered_at    TIMESTAMP,
    version         INT DEFAULT 0  -- optimistic locking
);

CREATE INDEX idx_orders_customer ON orders(customer_id, placed_at DESC);
CREATE INDEX idx_orders_status ON orders(status) WHERE status NOT IN ('DELIVERED','CANCELLED');
```

### Delivery Service (MongoDB)

```javascript
{
  _id: ObjectId,
  driver_id: "uuid",
  order_id: "uuid",
  location_trail: [
    { lat: 12.97, lng: 77.59, ts: ISODate("2026-05-30T10:30:00Z") }
  ],
  current_location: { type: "Point", coordinates: [77.59, 12.97] },
  status: "EN_ROUTE",
  createdAt: ISODate  // TTL index: auto-delete after 30 days
}
```

---

## AI Integration Roadmap (Future Phases)


| Phase       | AI Feature                | Model / Approach                                                    | Impact                             |
| ----------- | ------------------------- | ------------------------------------------------------------------- | ---------------------------------- |
| **Phase 1** | **ETA Prediction**        | Regression model (XGBoost) on historical delivery data              | Accurate delivery times            |
| **Phase 2** | **Smart Recommendations** | Collaborative filtering + content-based (user order history + tags) | Higher order value                 |
| **Phase 3** | **Demand Forecasting**    | Time-series model (Prophet/LSTM) per zone                           | Pre-position drivers, reduce idle  |
| **Phase 4** | **Dynamic Pricing**       | Surge pricing model based on demand/supply ratio per zone           | Revenue optimization               |
| **Phase 5** | **AI Chatbot (Support)**  | LLM (GPT/Claude API) with RAG over order data                       | Reduce support tickets by 60%      |
| **Phase 6** | **Fraud Detection**       | Anomaly detection on payment patterns                               | Prevent fake orders, payment fraud |
| **Phase 7** | **Route Optimization**    | OR-Tools / custom algorithm for multi-drop delivery batching        | Reduce delivery cost per order     |


### AI Architecture Integration Point

```
┌───────────────┐     ┌──────────────┐     ┌──────────────┐
│  Java Backend │────▶│  AI Service  │────▶│  ML Models   │
│  (Spring Boot)│◀────│  (Python     │     │  (MLflow)    │
│               │ REST│   FastAPI)   │     │              │
└───────────────┘     └──────────────┘     └──────────────┘
                             │
                      ┌──────▼───────┐
                      │  Feature     │
                      │  Store       │
                      │  (Redis)     │
                      └──────────────┘
```

The Java backend calls a Python-based AI microservice via REST. This keeps ML/AI in Python (where the ecosystem is strongest) while the core platform stays in Java.

---

## React Frontend Structure

```
src/
├── apps/
│   ├── customer/        # Food ordering UI
│   ├── restaurant/      # Order management dashboard
│   ├── driver/          # Delivery app (mobile-first)
│   └── admin/           # Analytics & operations
├── components/
│   ├── Map/             # Leaflet/Mapbox real-time tracking
│   ├── OrderTracker/    # Live order status with SSE
│   ├── Chat/            # AI chatbot widget (future)
│   └── Charts/          # Recharts/D3 for analytics
├── hooks/
│   ├── useWebSocket.ts
│   ├── useOrderStream.ts
│   └── useGeolocation.ts
├── store/               # Redux Toolkit slices
└── api/                 # React Query + Axios
```

---

## How to Build This Incrementally


| Sprint    | Scope                                                                   |
| --------- | ----------------------------------------------------------------------- |
| **1-2**   | User auth + Restaurant CRUD + Menu (monolith, PostgreSQL, React basics) |
| **3-4**   | Order flow + Payment stub + Order tracking page                         |
| **5-6**   | Add Kafka for async events, extract Notification Service                |
| **7-8**   | Delivery Service + real-time tracking (WebSocket + map)                 |
| **9-10**  | Search with Elasticsearch, caching with Redis                           |
| **11-12** | Extract to microservices, add API Gateway, Docker Compose               |
| **13-14** | Admin dashboard + analytics                                             |
| **15+**   | AI features (start with ETA prediction)                                 |


---

## Summary

This project covers every major system design concept:

- **Microservices** & modular monolith migration path
- **Event-driven architecture** with Kafka
- **CQRS** & **Saga pattern** for distributed transactions
- **CAP theorem** tradeoffs in practice
- **Polyglot persistence** (PostgreSQL + MongoDB + Redis + Elasticsearch)
- **Real-time communication** (WebSocket, SSE, polling)
- **API Gateway**, circuit breakers, rate limiting
- **AI/ML integration** roadmap with clear separation of concerns

All with clear, defensible tradeoff decisions at each layer.