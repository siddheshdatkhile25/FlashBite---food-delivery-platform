# BE-022: Operating Hours & Holiday Schedule

| Field | Value |
|-------|-------|
| **ID** | BE-022 |
| **Module** | `restaurant-service` |
| **Sprint** | 2 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | RM-03 |

## Summary

Weekly operating hours and holiday closures; restaurant auto-hidden outside business hours.

## Acceptance Criteria

- [ ] `PUT /api/v1/restaurant/hours` sets weekly schedule (day → open/close times)
- [ ] `POST /api/v1/restaurant/holidays` adds closure dates
- [ ] `isOpenNow(restaurantId)` computed from hours + timezone
- [ ] Customer search and menu endpoints filter or flag closed restaurants
- [ ] Cache `isOpen` with 30s TTL (stale OK per PROJECT_PLAN)

## Data Model

```sql
restaurant_hours (restaurant_id, day_of_week, open_time, close_time)
restaurant_holidays (restaurant_id, date, reason)
```

## Dependencies

- BE-020

## Definition of Done

- [ ] Unit tests for open/closed edge cases (midnight crossover, holidays)
