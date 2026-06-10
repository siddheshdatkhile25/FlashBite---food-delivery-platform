# BE-073: Recently Viewed & Reorder Support

| Field | Value |
|-------|-------|
| **ID** | BE-073 |
| **Module** | `search-service` + `order-service` |
| **Sprint** | 9–10 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | SD-05 |

## Summary

Track recently viewed restaurants and expose reorder shortcuts from order history.

## Acceptance Criteria

- [ ] `POST /api/v1/restaurants/{id}/viewed` records view (Redis sorted set, last 20)
- [ ] `GET /api/v1/users/me/recently-viewed` returns restaurant summaries
- [ ] Reorder via BE-035 `POST /api/v1/orders/{id}/reorder`
- [ ] Homepage API aggregates recent views + past orders (BFF optional)

## Dependencies

- BE-035, BE-071, Redis

## Definition of Done

- [ ] Integration test: view restaurant → appears in recently viewed
