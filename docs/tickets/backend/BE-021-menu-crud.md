# BE-021: Menu CRUD

| Field | Value |
|-------|-------|
| **ID** | BE-021 |
| **Module** | `restaurant-service` |
| **Sprint** | 1–2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | RM-02 |

## Summary

Full menu management: categories, items, prices, images, descriptions, availability flag.

## Acceptance Criteria

- [ ] CRUD `/api/v1/restaurant/menu/categories`
- [ ] CRUD `/api/v1/restaurant/menu/items` with category, name, price, image, description
- [ ] `GET /api/v1/restaurants/{id}/menu` (public) returns menu for customers
- [ ] Menu changes propagate to search index within 30s (via event or sync call)
- [ ] Restaurant can only modify own menu (RBAC)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET/POST/PATCH/DELETE | `/api/v1/restaurant/menu/items` | Manage items |
| GET | `/api/v1/restaurants/{id}/menu` | Public menu |

## Dependencies

- BE-020, BE-013

## Definition of Done

- [ ] Integration test: create category → add items → public GET menu
