# BE-072: Search Filters & Sorting

| Field | Value |
|-------|-------|
| **ID** | BE-072 |
| **Module** | `search-service` |
| **Sprint** | 9–10 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | SD-03, SD-04 |

## Summary

Combinable filters and sort options on restaurant search.

## Acceptance Criteria

- [ ] Filters: `cuisine`, `minRating`, `priceRange`, `maxDeliveryTime` (combinable)
- [ ] Sort: `relevance` (default), `distance`, `rating`, `deliveryTime`, `popularity`
- [ ] Faceted filter counts in response (optional)
- [ ] Redis cache for hot queries (30–60s TTL)

## Query Parameters

```
?cuisine=indian&minRating=4&sort=distance&priceRange=budget
```

## Dependencies

- BE-071

## Definition of Done

- [ ] Integration test: combined filters return expected subset
