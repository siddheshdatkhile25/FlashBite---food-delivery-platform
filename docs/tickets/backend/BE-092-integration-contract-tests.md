# BE-092: Integration & Contract Tests

| Field | Value |
|-------|-------|
| **ID** | BE-092 |
| **Module** | all services |
| **Sprint** | 13–14 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | §4.6 Testing |

## Summary

Testcontainers-based integration tests and Pact contract tests between service pairs.

## Acceptance Criteria

- [ ] Integration tests for critical paths: register → order → pay → deliver
- [ ] Testcontainers: Postgres, Redis, Kafka, MongoDB, Elasticsearch
- [ ] Pact contracts: order→payment, order→notification, restaurant→order events
- [ ] CI runs `mvn verify` on every PR
- [ ] > 80% line coverage on order, payment, user modules

## Critical Test Scenarios

1. Happy path: place order → payment success → delivered
2. Cancellation before confirm → refund
3. Payment failure → retry → success
4. Driver reject → reassignment

## Dependencies

- Core features through BE-056 implemented

## Definition of Done

- [ ] CI green with integration test profile
- [ ] Coverage report published in CI artifacts
