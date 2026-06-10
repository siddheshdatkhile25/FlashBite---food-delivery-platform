# BE-001: Common Shared Module

| Field | Value |
|-------|-------|
| **ID** | BE-001 |
| **Module** | `common` |
| **Sprint** | 1 |
| **Priority** | P0 |
| **Status** | Done |
| **PRD** | §4.4 Security, §4.5 Observability |

## Summary

Implement the shared `common` Maven module with DTOs, Kafka event schemas, global error handling, security utilities, and cross-cutting constants used by all backend services.

## Scope

- Standard API error response: `{ code, message, traceId, details? }`
- Base event envelope: `eventId`, `timestamp`, `aggregateId`, `version`, `payload`
- Shared enums: `UserRole`, `OrderStatus`, `PaymentStatus`, `DeliveryStatus`
- JWT claim constants and RBAC annotation helpers
- `Idempotency-Key` header constant and validation utility
- Trace ID propagation filter (MDC)

## Acceptance Criteria

- [x] `common` module builds and is importable by all service modules
- [x] Error response shape documented and used in at least one service
- [x] Kafka event base classes match PRD §8 topic catalog
- [x] All enums align with PRD order lifecycle (`PLACED → CONFIRMED → PREPARING → READY → PICKED_UP → DELIVERED`)
- [x] No Spring Boot application in `common` (library only)

## Technical Notes

- Package: `com.flashbite.common`
- Add dependencies: `spring-boot-starter-validation`, Jackson, optional Kafka types
- Keep `common` free of service-specific business logic

## Dependencies

- None (first ticket)

## Definition of Done

- [x] `mvn clean install` passes for `backend/common`
- [x] Unit tests for error mapping and event serialization
- [x] README snippet in module or ARCHITECTURE.md updated
