# BE-071: Full-Text & Geo Search

| Field | Value |
|-------|-------|
| **ID** | BE-071 |
| **Module** | `search-service` |
| **Sprint** | 9–10 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | SD-01, SD-02 |

## Summary

Search restaurants and menu items by text query and geo radius.

## Acceptance Criteria

- [ ] `GET /api/v1/restaurants?lat=&lng=&q=&radiusKm=5` returns ranked results
- [ ] Full-text across restaurant name and menu item names
- [ ] Default radius 5 km; user-adjustable
- [ ] P95 response < 200ms with warm cache
- [ ] Closed restaurants deprioritized or filtered

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/restaurants` | Search & discover |
| GET | `/api/v1/restaurants/{id}/menu` | Menu (may proxy restaurant-service) |

## Dependencies

- BE-070, BE-022

## Definition of Done

- [ ] Integration test with seeded ES data
- [ ] Latency benchmark documented
