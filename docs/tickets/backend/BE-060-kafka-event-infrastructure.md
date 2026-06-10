# BE-060: Kafka Event Infrastructure

| Field | Value |
|-------|-------|
| **ID** | BE-060 |
| **Module** | `common` + all services |
| **Sprint** | 5–6 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | §8 Event Catalog |

## Summary

Kafka producers, consumers, topic setup, idempotent processing, and dead-letter queue for all domain events.

## Scope

Topics (from PRD §8):

| Topic | Producer | Consumers |
|-------|----------|-----------|
| `order.placed` | order-service | payment, notification |
| `payment.success` | payment-service | order, restaurant, notification |
| `payment.failed` | payment-service | order, notification |
| `order.confirmed` | restaurant-service | order, delivery, notification |
| `order.ready` | restaurant-service | delivery, notification |
| `driver.assigned` | delivery-service | order, notification |
| `driver.location.updated` | delivery-service | order (SSE bridge) |
| `order.delivered` | delivery-service | order, payment, notification |
| `order.cancelled` | order-service | payment, notification |
| `review.submitted` | order-service | restaurant, analytics |

## Acceptance Criteria

- [ ] Spring Kafka configured in all event-emitting services
- [ ] Event envelope from BE-001 used consistently
- [ ] Consumers dedupe by `eventId` (Redis or DB)
- [ ] DLQ topic `{original}.dlq` after 3 retries
- [ ] Alert hook after DLQ threshold (BE-091)
- [ ] Local dev: Kafka in docker-compose on 9092

## Dependencies

- BE-001, docker-compose Kafka

## Definition of Done

- [ ] Integration test: publish → consume → ack
- [ ] DLQ test: poison message lands in DLQ after retries
