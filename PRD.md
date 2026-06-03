# Product Requirements Document (PRD)

## FlashBite — AI-Ready Real-Time Food Delivery & Logistics Platform


| Field            | Detail                                                 |
| ---------------- | ------------------------------------------------------ |
| **Product Name** | FlashBite                                              |
| **Version**      | 1.0 (MVP)                                              |
| **Author**       | Engineering Team                                       |
| **Date**         | May 30, 2026                                           |
| **Status**       | Draft                                                  |
| **Tech Stack**   | Java 17 / Spring Boot 3 (Backend), React 18 (Frontend) |


---

## 1. Executive Summary

FlashBite is a real-time food delivery and logistics platform that connects customers, restaurants, and delivery drivers through a unified digital experience. The platform handles the full lifecycle — from restaurant discovery and ordering to payment processing, real-time delivery tracking, and post-delivery analytics.

The system is architected from day one to support AI integration in later phases, including ETA prediction, smart recommendations, demand forecasting, dynamic pricing, and an AI-powered support chatbot.

### 1.1 Business Objectives


| Objective                     | Target Metric                               |
| ----------------------------- | ------------------------------------------- |
| Enable seamless food ordering | < 3 clicks from search to checkout          |
| Real-time delivery visibility | Live tracking with < 5s location lag        |
| Fast delivery                 | Average delivery time < 35 minutes          |
| Platform reliability          | 99.9% uptime for order placement            |
| Restaurant onboarding         | Onboard a new restaurant in < 10 minutes    |
| Customer retention            | 40% repeat order rate within 30 days        |
| AI-readiness                  | Data pipelines in place for ML from day one |


### 1.2 Success Criteria

- MVP launched and handling 1,000+ concurrent users
- End-to-end order flow (place → pay → track → deliver) working with < 2s latency on each step
- 95th percentile API response time under 300ms
- Zero payment data loss (strong consistency for financial transactions)

---

## 2. Target Users & Personas

### 2.1 Customer (End User)


| Attribute       | Detail                                                                   |
| --------------- | ------------------------------------------------------------------------ |
| **Who**         | Anyone ordering food for delivery or pickup                              |
| **Goals**       | Find food quickly, get accurate ETAs, track delivery in real time        |
| **Pain Points** | Slow search, inaccurate delivery estimates, no visibility after ordering |
| **Devices**     | Mobile (primary), Desktop (secondary)                                    |


### 2.2 Restaurant Partner


| Attribute       | Detail                                                                        |
| --------------- | ----------------------------------------------------------------------------- |
| **Who**         | Restaurant owners and kitchen staff                                           |
| **Goals**       | Manage menu and availability, receive and process orders efficiently          |
| **Pain Points** | Missed orders, inability to update menu in real time, lack of sales analytics |
| **Devices**     | Tablet (primary), Desktop (secondary)                                         |


### 2.3 Delivery Driver


| Attribute       | Detail                                                                        |
| --------------- | ----------------------------------------------------------------------------- |
| **Who**         | Freelance or full-time delivery personnel                                     |
| **Goals**       | Receive orders quickly, get optimized routes, maximize earnings               |
| **Pain Points** | Poor route suggestions, unclear pickup instructions, delayed order assignment |
| **Devices**     | Mobile (only)                                                                 |


### 2.4 Platform Admin


| Attribute       | Detail                                                    |
| --------------- | --------------------------------------------------------- |
| **Who**         | Operations team, support agents, business analysts        |
| **Goals**       | Monitor platform health, resolve disputes, analyze trends |
| **Pain Points** | Lack of real-time dashboards, manual refund processes     |
| **Devices**     | Desktop (only)                                            |


---

## 3. Functional Requirements

### 3.1 User Management (P0 — Must Have)


| ID    | Requirement                                                     | Acceptance Criteria                                   |
| ----- | --------------------------------------------------------------- | ----------------------------------------------------- |
| UM-01 | User registration with email/phone + password                   | Account created, verification email/SMS sent          |
| UM-02 | OAuth2 social login (Google, Facebook)                          | One-click sign-in, profile auto-populated             |
| UM-03 | Role-based access control (customer, restaurant, driver, admin) | Each role sees only their permitted views/APIs        |
| UM-04 | Profile management (name, photo, addresses, preferences)        | Users can update profile; changes reflected instantly |
| UM-05 | JWT-based session management with refresh tokens                | Access token expires in 15 min, refresh in 7 days     |
| UM-06 | Password reset via email OTP                                    | OTP valid for 10 min, one-time use                    |


### 3.2 Restaurant Management (P0 — Must Have)


| ID    | Requirement                                                 | Acceptance Criteria                            |
| ----- | ----------------------------------------------------------- | ---------------------------------------------- |
| RM-01 | Restaurant registration and profile setup                   | Name, address, cuisine, hours, logo uploaded   |
| RM-02 | Menu CRUD (categories, items, prices, images, availability) | Menu changes reflected to customers within 30s |
| RM-03 | Operating hours and holiday schedule management             | Restaurant auto-hidden outside business hours  |
| RM-04 | Toggle item availability (out-of-stock)                     | Item grayed out in customer UI immediately     |
| RM-05 | View and manage incoming orders                             | Orders appear within 2s of placement           |
| RM-06 | Update order status (confirm, preparing, ready for pickup)  | Status change triggers customer notification   |
| RM-07 | Revenue and order analytics dashboard                       | Daily/weekly/monthly views with export to CSV  |


### 3.3 Search & Discovery (P0 — Must Have)


| ID    | Requirement                                                    | Acceptance Criteria                             |
| ----- | -------------------------------------------------------------- | ----------------------------------------------- |
| SD-01 | Full-text search across restaurant names and menu items        | Results returned in < 200ms                     |
| SD-02 | Geo-based restaurant discovery (within configurable radius)    | Default 5 km radius, adjustable by user         |
| SD-03 | Filter by cuisine, rating, price range, delivery time          | Filters combinable; results update in real time |
| SD-04 | Sort by relevance, distance, rating, delivery time, popularity | Default sort: relevance                         |
| SD-05 | Recently viewed and reorder from past orders                   | Accessible from homepage                        |


### 3.4 Order Management (P0 — Must Have)


| ID    | Requirement                                                                | Acceptance Criteria                                                                |
| ----- | -------------------------------------------------------------------------- | ---------------------------------------------------------------------------------- |
| OM-01 | Add items to cart with quantity and customization notes                    | Cart persists across sessions (server-side)                                        |
| OM-02 | Cart validation (restaurant availability, minimum order, items in stock)   | Validation errors shown before checkout                                            |
| OM-03 | Checkout with delivery address selection and order summary                 | Address auto-filled from saved addresses                                           |
| OM-04 | Order confirmation with estimated delivery time                            | Confirmation screen within 2s of payment                                           |
| OM-05 | Order lifecycle management                                                 | Status transitions: PLACED → CONFIRMED → PREPARING → READY → PICKED_UP → DELIVERED |
| OM-06 | Order cancellation (within 60s of placement or before restaurant confirms) | Refund auto-triggered on cancellation                                              |
| OM-07 | Order history with reorder capability                                      | Past 6 months of orders visible                                                    |
| OM-08 | Real-time order status updates via SSE                                     | Customer sees status change within 3s                                              |


### 3.5 Payment Processing (P0 — Must Have)


| ID    | Requirement                                                        | Acceptance Criteria                                   |
| ----- | ------------------------------------------------------------------ | ----------------------------------------------------- |
| PM-01 | Multiple payment methods (card, UPI, wallet, cash on delivery)     | At least 2 methods supported at MVP                   |
| PM-02 | Payment gateway integration (Stripe / Razorpay)                    | PCI-DSS compliant; no raw card data stored            |
| PM-03 | Order total calculation (items + taxes + delivery fee - discounts) | Itemized breakdown visible before payment             |
| PM-04 | Refund processing (full / partial)                                 | Refund initiated within 24h, credited within 5-7 days |
| PM-05 | Payment failure handling with retry                                | User prompted to retry; order held for 5 min          |
| PM-06 | Transaction history for all users                                  | Downloadable as PDF/CSV                               |
| PM-07 | Restaurant payout (settlement) dashboard                           | Weekly settlement summary with line-item detail       |


### 3.6 Delivery & Logistics (P0 — Must Have)


| ID    | Requirement                                                        | Acceptance Criteria                                   |
| ----- | ------------------------------------------------------------------ | ----------------------------------------------------- |
| DL-01 | Automatic driver assignment based on proximity and availability    | Driver assigned within 60s of restaurant confirmation |
| DL-02 | Driver accepts/rejects order assignment                            | 30s window to accept; auto-reassign on timeout/reject |
| DL-03 | Real-time driver location tracking (GPS every 5s)                  | Customer sees live map with driver pin                |
| DL-04 | Delivery status updates (picked up, en route, arriving, delivered) | Each status change triggers push notification         |
| DL-05 | Proof of delivery (customer confirms or photo upload)              | Order marked delivered only after proof               |
| DL-06 | Driver earnings dashboard                                          | Per-order breakdown, daily/weekly summary             |
| DL-07 | Driver availability toggle (online/offline)                        | Driver only receives assignments when online          |


### 3.7 Notifications (P0 — Must Have)


| ID    | Requirement                                                     | Acceptance Criteria                  |
| ----- | --------------------------------------------------------------- | ------------------------------------ |
| NF-01 | Push notifications for order status changes                     | Delivered within 5s of status change |
| NF-02 | SMS notifications for critical events (order placed, delivered) | SMS sent via Twilio/SNS              |
| NF-03 | Email notifications (order confirmation, receipts, promotions)  | Email delivered within 1 min         |
| NF-04 | In-app notification center                                      | Unread badge count, mark as read     |
| NF-05 | Notification preferences (opt-in/opt-out per channel)           | Preferences stored per user          |


### 3.8 Ratings & Reviews (P1 — Should Have)


| ID    | Requirement                                                     | Acceptance Criteria                                   |
| ----- | --------------------------------------------------------------- | ----------------------------------------------------- |
| RR-01 | Customer rates restaurant and driver (1-5 stars) after delivery | Rating prompt shown after delivery confirmation       |
| RR-02 | Written review for restaurant                                   | Reviews visible on restaurant page, most recent first |
| RR-03 | Restaurant can respond to reviews                               | Response visible below the review                     |
| RR-04 | Average rating displayed on restaurant card and detail page     | Recalculated on each new rating                       |


### 3.9 Promotions & Coupons (P1 — Should Have)


| ID    | Requirement                                                | Acceptance Criteria                                        |
| ----- | ---------------------------------------------------------- | ---------------------------------------------------------- |
| PC-01 | Admin creates coupon codes (flat / percentage discount)    | Configurable: min order, max discount, expiry, usage limit |
| PC-02 | Customer applies coupon at checkout                        | Discount reflected in order total immediately              |
| PC-03 | Restaurant-specific promotions (e.g., 20% off on weekends) | Promotion banner visible on restaurant page                |
| PC-04 | First-order discount for new users                         | Auto-applied on first checkout                             |


### 3.10 Admin Panel (P1 — Should Have)


| ID    | Requirement                                                          | Acceptance Criteria                              |
| ----- | -------------------------------------------------------------------- | ------------------------------------------------ |
| AP-01 | Dashboard with live metrics (active orders, online drivers, revenue) | Data refreshes every 30s                         |
| AP-02 | Restaurant approval/suspension workflow                              | Admin approves before restaurant goes live       |
| AP-03 | Driver verification and approval workflow                            | Document upload → admin review → approval        |
| AP-04 | Customer/restaurant/driver management (search, view, suspend)        | Search by name, email, phone, ID                 |
| AP-05 | Dispute resolution (view order details, issue refunds)               | Full order audit trail visible                   |
| AP-06 | System health monitoring dashboard                                   | Service status, error rates, latency percentiles |


---

## 4. Non-Functional Requirements

### 4.1 Performance


| Metric                     | Target                   |
| -------------------------- | ------------------------ |
| API response time (P95)    | < 300ms                  |
| API response time (P99)    | < 1s                     |
| Order placement throughput | 500 orders/min sustained |
| Search query response      | < 200ms                  |
| WebSocket message latency  | < 500ms end-to-end       |
| Page load time (React SPA) | < 2s on 4G connection    |
| Database query time (P95)  | < 50ms                   |


### 4.2 Scalability


| Dimension          | Requirement                                                 |
| ------------------ | ----------------------------------------------------------- |
| Concurrent users   | Support 10,000 concurrent at MVP                            |
| Horizontal scaling | Each microservice independently scalable                    |
| Database scaling   | Read replicas for PostgreSQL; sharding strategy for MongoDB |
| Event throughput   | Kafka handles 10,000 events/sec per partition               |
| CDN                | Static assets served via CloudFront/Cloudflare              |


### 4.3 Reliability & Availability


| Metric                         | Target                                                       |
| ------------------------------ | ------------------------------------------------------------ |
| Uptime SLA                     | 99.9% (< 8.7 hours downtime/year)                            |
| RTO (Recovery Time Objective)  | < 15 minutes                                                 |
| RPO (Recovery Point Objective) | < 1 minute (for financial data)                              |
| Circuit breaker recovery       | Auto-recover within 30s of downstream recovery               |
| Dead-letter queue processing   | Failed events retried within 5 min, alerted after 3 failures |


### 4.4 Security


| Area               | Requirement                                                                |
| ------------------ | -------------------------------------------------------------------------- |
| Authentication     | JWT + OAuth2 with refresh token rotation                                   |
| Authorization      | Role-based access control (RBAC) enforced at API Gateway and service level |
| Data encryption    | TLS 1.3 in transit; AES-256 at rest for PII and payment data               |
| PCI compliance     | No raw card numbers stored; tokenized via payment gateway                  |
| Input validation   | Server-side validation on all endpoints; XSS/SQL injection prevention      |
| Rate limiting      | Per-user and per-IP rate limits at API Gateway                             |
| Audit logging      | All admin actions and payment events logged with timestamp and actor       |
| GDPR/Data privacy  | User data export and deletion on request within 72 hours                   |
| Secrets management | All secrets in Vault/AWS Secrets Manager; never in code or config files    |


### 4.5 Observability


| Area                | Tool / Approach                                   |
| ------------------- | ------------------------------------------------- |
| Metrics             | Prometheus + Grafana dashboards                   |
| Logging             | Structured JSON logs → ELK Stack                  |
| Distributed tracing | Micrometer + Zipkin/Jaeger                        |
| Alerting            | PagerDuty/Opsgenie for P0 incidents               |
| Health checks       | Spring Actuator `/health` on all services         |
| Business metrics    | Custom Kafka consumers for real-time KPI tracking |


### 4.6 Testing


| Type              | Coverage Target                           |
| ----------------- | ----------------------------------------- |
| Unit tests        | > 80% line coverage                       |
| Integration tests | All inter-service contracts tested        |
| Contract tests    | Pact-based for service API compatibility  |
| End-to-end tests  | Critical flows (order, payment, delivery) |
| Load tests        | Gatling/k6 simulating 10x expected peak   |
| Chaos testing     | Resilience4j fault injection in staging   |


---

## 5. User Flows

### 5.1 Customer Orders Food (Happy Path)

```
Customer opens app
    → App detects location (or user enters address)
    → Homepage shows nearby restaurants (geo-query)
    → Customer searches "biryani" (full-text search via Elasticsearch)
    → Selects restaurant → Views menu
    → Adds items to cart → Proceeds to checkout
    → Selects delivery address → Applies coupon (optional)
    → Reviews order summary (items + tax + delivery fee - discount)
    → Chooses payment method → Pays
    → Order PLACED → Kafka event published
    → Payment Service deducts amount → PAYMENT_SUCCESS event
    → Restaurant receives order → Confirms → CONFIRMED status
    → Restaurant marks PREPARING → then READY
    → Delivery Service assigns nearest driver → DRIVER_ASSIGNED
    → Driver picks up → PICKED_UP
    → Customer tracks driver on live map (WebSocket/SSE)
    → Driver delivers → DELIVERED
    → Customer rates restaurant & driver
    → Receipt emailed
```

### 5.2 Order Cancellation (Compensation Flow)

```
Customer requests cancellation
    → System checks: is order already CONFIRMED by restaurant?
        → If No: cancel immediately, trigger PAYMENT_REFUND event
        → If Yes but not PREPARING: cancel with restaurant notification
        → If PREPARING or later: cancellation denied (show message)
    → Saga compensation:
        → CANCEL_ORDER event → Payment Service processes refund
        → Notification Service sends cancellation confirmation
        → Driver unassigned (if assigned)
```

### 5.3 Driver Assignment (Retry Flow)

```
Order marked READY by restaurant
    → Delivery Service queries nearby online drivers (geo-query on MongoDB)
    → Assigns closest driver → Push notification sent
    → Driver has 30s to accept
        → Accepted: DRIVER_ASSIGNED, customer notified
        → Rejected/Timeout: reassign to next closest driver
        → After 3 failed attempts: alert ops team, extend ETA for customer
```

---

## 6. Data Model Overview

### 6.1 Core Entities and Relationships

```
┌──────────┐       ┌──────────────┐       ┌──────────────┐
│   User   │──1:N──│   Address    │       │  Restaurant  │
│          │       └──────────────┘       │              │
│          │──1:N──┐                      │              │──1:N──┌──────────┐
└──────────┘       │                      └──────────────┘       │ MenuItem │
                   │                             │               └──────────┘
              ┌────▼─────┐                       │
              │  Order   │───────────────────────┘
              │          │──1:1──┌──────────────┐
              │          │       │   Payment    │
              │          │       └──────────────┘
              │          │──1:1──┌──────────────┐
              └──────────┘       │  Delivery    │
                                 │  Assignment  │──1:1──┌────────┐
                                 └──────────────┘       │ Driver │
                                                        └────────┘
```

### 6.2 Storage Mapping


| Entity                | Database      | Reason                                      |
| --------------------- | ------------- | ------------------------------------------- |
| User, Address         | PostgreSQL    | Relational integrity, ACID                  |
| Restaurant, MenuItem  | PostgreSQL    | Relational integrity, transactional updates |
| Order, OrderItem      | PostgreSQL    | Financial data, strong consistency          |
| Payment, Refund       | PostgreSQL    | ACID critical, audit trail                  |
| Delivery Assignment   | PostgreSQL    | Transactional (linked to order)             |
| Driver Location Trail | MongoDB       | High-frequency writes, TTL, geo-indexes     |
| Search Index          | Elasticsearch | Full-text + geo queries                     |
| Session, Cache, OTP   | Redis         | Low-latency, ephemeral data                 |
| Event Log             | Kafka         | Async processing, replay capability         |


---

## 7. API Overview

### 7.1 Customer APIs


| Method | Endpoint                           | Description                   |
| ------ | ---------------------------------- | ----------------------------- |
| POST   | `/api/v1/auth/register`            | Register new customer         |
| POST   | `/api/v1/auth/login`               | Login, returns JWT            |
| GET    | `/api/v1/restaurants?lat=&lng=&q=` | Search restaurants            |
| GET    | `/api/v1/restaurants/{id}/menu`    | Get restaurant menu           |
| POST   | `/api/v1/cart/items`               | Add item to cart              |
| GET    | `/api/v1/cart`                     | Get current cart              |
| POST   | `/api/v1/orders`                   | Place order                   |
| GET    | `/api/v1/orders/{id}`              | Get order details             |
| GET    | `/api/v1/orders/{id}/track`        | SSE stream for order tracking |
| POST   | `/api/v1/orders/{id}/cancel`       | Cancel order                  |
| POST   | `/api/v1/orders/{id}/rate`         | Rate restaurant & driver      |
| GET    | `/api/v1/orders/history`           | Past orders                   |


### 7.2 Restaurant APIs


| Method | Endpoint                                          | Description            |
| ------ | ------------------------------------------------- | ---------------------- |
| GET    | `/api/v1/restaurant/orders`                       | Incoming orders (live) |
| PATCH  | `/api/v1/restaurant/orders/{id}/status`           | Update order status    |
| CRUD   | `/api/v1/restaurant/menu/items`                   | Manage menu items      |
| PATCH  | `/api/v1/restaurant/menu/items/{id}/availability` | Toggle availability    |
| GET    | `/api/v1/restaurant/analytics`                    | Revenue & order stats  |


### 7.3 Driver APIs


| Method | Endpoint                            | Description               |
| ------ | ----------------------------------- | ------------------------- |
| PATCH  | `/api/v1/driver/status`             | Go online/offline         |
| GET    | `/api/v1/driver/orders/current`     | Current active assignment |
| POST   | `/api/v1/driver/orders/{id}/accept` | Accept assignment         |
| POST   | `/api/v1/driver/orders/{id}/reject` | Reject assignment         |
| POST   | `/api/v1/driver/location`           | Update GPS coordinates    |
| PATCH  | `/api/v1/driver/orders/{id}/status` | Update delivery status    |
| GET    | `/api/v1/driver/earnings`           | Earnings summary          |


### 7.4 Admin APIs


| Method | Endpoint                                 | Description           |
| ------ | ---------------------------------------- | --------------------- |
| GET    | `/api/v1/admin/dashboard`                | Live platform metrics |
| GET    | `/api/v1/admin/users?search=`            | Search users          |
| PATCH  | `/api/v1/admin/users/{id}/suspend`       | Suspend user          |
| PATCH  | `/api/v1/admin/restaurants/{id}/approve` | Approve restaurant    |
| POST   | `/api/v1/admin/refunds`                  | Issue manual refund   |
| CRUD   | `/api/v1/admin/coupons`                  | Manage coupons        |


---

## 8. Event Catalog (Kafka Topics)


| Topic                     | Producer         | Consumer(s)                         | Payload Highlights            |
| ------------------------- | ---------------- | ----------------------------------- | ----------------------------- |
| `order.placed`            | Order Service    | Payment, Notification               | orderId, customerId, amount   |
| `payment.success`         | Payment Service  | Order, Restaurant, Notification     | orderId, transactionId        |
| `payment.failed`          | Payment Service  | Order, Notification                 | orderId, reason               |
| `order.confirmed`         | Restaurant       | Order, Delivery, Notification       | orderId, estimatedPrepTime    |
| `order.ready`             | Restaurant       | Delivery, Notification              | orderId                       |
| `driver.assigned`         | Delivery Service | Order, Notification                 | orderId, driverId, eta        |
| `driver.location.updated` | Driver App       | Delivery Service, Customer (via WS) | driverId, lat, lng, timestamp |
| `order.delivered`         | Driver App       | Order, Payment, Notification        | orderId, deliveredAt          |
| `order.cancelled`         | Order Service    | Payment (refund), Notification      | orderId, reason, refundAmount |
| `review.submitted`        | Order Service    | Restaurant, Analytics               | orderId, rating, reviewText   |


---

## 9. AI Features (Future Phases)

These features are **not in MVP** but the data pipelines and storage are designed to support them from day one.

### Phase 1: ETA Prediction (Post-MVP + 1 month)

- **Input**: Historical delivery data (distance, time of day, weather, traffic zone, restaurant prep time)
- **Model**: XGBoost regression
- **Integration**: Python FastAPI microservice called by Order Service at checkout
- **Impact**: Replace static ETA estimates with data-driven predictions (target: < 5 min error margin)

### Phase 2: Smart Recommendations (Post-MVP + 2 months)

- **Input**: User order history, item tags, time of day, popular items in zone
- **Model**: Collaborative filtering + content-based hybrid
- **Integration**: Recommendations API called by frontend on homepage and restaurant page
- **Impact**: Increase average order value by 15%

### Phase 3: Demand Forecasting (Post-MVP + 3 months)

- **Input**: Historical order volume per zone per hour, events calendar, weather
- **Model**: Prophet / LSTM time-series
- **Integration**: Batch job producing hourly forecasts consumed by Delivery Service
- **Impact**: Pre-position drivers in high-demand zones; reduce average delivery time by 20%

### Phase 4: Dynamic Pricing (Post-MVP + 4 months)

- **Input**: Real-time supply (online drivers) vs demand (incoming orders) per zone
- **Model**: Rule-based initially, ML-based later
- **Integration**: Pricing Service applies multiplier at checkout
- **Impact**: Balance supply-demand; increase revenue during peak by 25%

### Phase 5: AI Support Chatbot (Post-MVP + 5 months)

- **Input**: Order data, FAQ knowledge base, past support tickets
- **Model**: LLM (GPT/Claude) with RAG over internal data
- **Integration**: Chat widget in React frontend, backend orchestration service
- **Impact**: Resolve 60% of support queries without human agent

### Phase 6: Fraud Detection (Post-MVP + 6 months)

- **Input**: Payment patterns, order frequency, device fingerprints, location anomalies
- **Model**: Isolation Forest / Autoencoder anomaly detection
- **Integration**: Real-time scoring on each payment event via Kafka consumer
- **Impact**: Prevent fake orders, reduce chargebacks by 40%

### Phase 7: Route Optimization (Post-MVP + 7 months)

- **Input**: Multiple pending deliveries, driver location, traffic data
- **Model**: OR-Tools CVRP solver / custom heuristic
- **Integration**: Delivery Service batches nearby orders and optimizes routes
- **Impact**: Reduce per-order delivery cost by 30% via multi-drop batching

---

## 10. Constraints & Assumptions

### Constraints

- MVP targets a single city/region; multi-city support is Phase 2
- Payment gateway is third-party (Stripe/Razorpay); no in-house payment processing
- Driver app is mobile-first responsive web, not a native app at MVP
- AI features require minimum 3 months of production data before training
- Kubernetes deployment deferred to post-MVP; MVP uses Docker Compose

### Assumptions

- Restaurants have internet-connected devices (tablet/desktop) to manage orders
- Drivers have GPS-enabled smartphones with stable mobile data
- Average order has 2-5 items from a single restaurant (no multi-restaurant cart at MVP)
- Peak traffic is 10x average traffic (e.g., lunch/dinner rush, weekends)
- Users are comfortable with digital payments (card/UPI/wallet)

---

## 11. Risks & Mitigations


| Risk                                         | Impact   | Probability | Mitigation                                        |
| -------------------------------------------- | -------- | ----------- | ------------------------------------------------- |
| Payment gateway downtime                     | Critical | Low         | Queue payments for retry; support COD as fallback |
| Driver supply shortage during peak           | High     | Medium      | Surge pricing incentives; AI demand forecasting   |
| Data loss during Kafka broker failure        | Critical | Low         | Replication factor 3; ISR-based acknowledgments   |
| High latency on search during traffic spikes | Medium   | Medium      | Elasticsearch cluster scaling; Redis query cache  |
| Restaurant fails to confirm order in time    | Medium   | Medium      | Auto-confirm after 5 min with alert; timeout SLA  |
| Security breach / data leak                  | Critical | Low         | Encryption, WAF, penetration testing, bug bounty  |
| AI model bias in recommendations             | Medium   | Medium      | A/B testing, fairness metrics, human review       |


---

## 12. Release Plan


| Release        | Scope                                                               | Timeline     |
| -------------- | ------------------------------------------------------------------- | ------------ |
| **Alpha**      | User auth, restaurant/menu CRUD, basic order flow, stub payment     | Sprint 1-4   |
| **Beta**       | Full payment integration, delivery tracking, notifications, search  | Sprint 5-10  |
| **MVP (v1.0)** | Admin panel, ratings, coupons, monitoring, load testing complete    | Sprint 11-14 |
| **v1.1**       | AI ETA prediction, smart recommendations                            | Sprint 15-18 |
| **v1.2**       | Demand forecasting, dynamic pricing, AI chatbot                     | Sprint 19-24 |
| **v2.0**       | Multi-city, native mobile apps, fraud detection, route optimization | Sprint 25+   |


---

## 13. Open Questions


| #   | Question                                                            | Owner       | Status |
| --- | ------------------------------------------------------------------- | ----------- | ------ |
| 1   | Which payment gateway (Stripe vs Razorpay vs both)?                 | Engineering | Open   |
| 2   | Should we support multi-restaurant cart at MVP?                     | Product     | Open   |
| 3   | Scheduled orders (pre-order for later) — MVP or v1.1?               | Product     | Open   |
| 4   | Driver onboarding — self-serve or admin-verified?                   | Operations  | Open   |
| 5   | What is the commission structure for restaurants?                   | Business    | Open   |
| 6   | Do we need multilingual support at MVP?                             | Product     | Open   |
| 7   | Should live chat with support agent be available before AI chatbot? | Product     | Open   |


---

## 14. Glossary


| Term     | Definition                                                                |
| -------- | ------------------------------------------------------------------------- |
| **Saga** | Distributed transaction pattern using compensating actions for rollback   |
| **CQRS** | Command Query Responsibility Segregation — separate read and write models |
| **SSE**  | Server-Sent Events — server pushes updates to client over HTTP            |
| **DLQ**  | Dead Letter Queue — stores failed events for retry/inspection             |
| **BFF**  | Backend For Frontend — API layer tailored to a specific frontend          |
| **ETA**  | Estimated Time of Arrival                                                 |
| **RAG**  | Retrieval Augmented Generation — LLM grounded with domain data            |
| **CVRP** | Capacitated Vehicle Routing Problem                                       |
| **ISR**  | In-Sync Replicas (Kafka) — replicas that are caught up with the leader    |


---

*This is a living document. Update it as product decisions are made and requirements evolve.*