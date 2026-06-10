# BE-070: Elasticsearch Indexing Pipeline

| Field | Value |
|-------|-------|
| **ID** | BE-070 |
| **Module** | `search-service` |
| **Sprint** | 9–10 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | SD-01, SD-02 |

## Summary

Index restaurants and menu items in Elasticsearch on create/update events.

## Acceptance Criteria

- [ ] Index `restaurants` with name, cuisine, location (geo_point), rating, isOpen
- [ ] Index `menu_items` nested or separate with restaurantId, name, price
- [ ] Kafka consumers on `restaurant.created`, menu update events
- [ ] Bulk reindex admin endpoint for recovery
- [ ] Index mapping versioned in repo

## Dependencies

- BE-020, BE-021, BE-060, Elasticsearch in docker-compose

## Definition of Done

- [ ] Integration test: create restaurant → document searchable within 30s
