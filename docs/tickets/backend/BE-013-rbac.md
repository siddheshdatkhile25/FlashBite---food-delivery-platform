# BE-013: Role-Based Access Control

| Field | Value |
|-------|-------|
| **ID** | BE-013 |
| **Module** | `user-service` + all services |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | UM-03 |

## Summary

Enforce RBAC for roles: `CUSTOMER`, `RESTAURANT`, `DRIVER`, `ADMIN`. Each role accesses only permitted APIs.

## Acceptance Criteria

- [ ] `@PreAuthorize` or custom filter enforces role on service endpoints
- [ ] Customer cannot call `/api/v1/restaurant/**` or `/api/v1/driver/**`
- [ ] Restaurant user scoped to own `restaurantId`
- [ ] Driver scoped to own `driverId`
- [ ] Admin can access `/api/v1/admin/**`
- [ ] RBAC enforced at service layer (defense in depth with BE-002 gateway)

## Role Matrix (summary)

| Endpoint prefix | CUSTOMER | RESTAURANT | DRIVER | ADMIN |
|-----------------|----------|------------|--------|-------|
| `/api/v1/cart`, `/api/v1/orders` (customer) | ✓ | | | ✓ |
| `/api/v1/restaurant/` | | ✓ | | ✓ |
| `/api/v1/driver/` | | | ✓ | ✓ |
| `/api/v1/admin/` | | | | ✓ |

## Dependencies

- BE-011

## Definition of Done

- [ ] Integration tests per role: allowed 200, forbidden 403
- [ ] RBAC matrix documented in ticket or ARCHITECTURE.md
