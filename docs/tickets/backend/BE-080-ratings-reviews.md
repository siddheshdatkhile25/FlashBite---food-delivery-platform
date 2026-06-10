# BE-080: Ratings & Reviews

| Field | Value |
|-------|-------|
| **ID** | BE-080 |
| **Module** | `order-service` + `restaurant-service` |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | RR-01 – RR-04 |

## Summary

Post-delivery ratings for restaurant and driver; written reviews with restaurant responses.

## Acceptance Criteria

- [ ] `POST /api/v1/orders/{id}/rate` with restaurantRating, driverRating, reviewText
- [ ] Only `DELIVERED` orders within 7 days rateable
- [ ] `GET /api/v1/restaurants/{id}/reviews` paginated, most recent first
- [ ] `POST /api/v1/restaurant/reviews/{id}/respond` for restaurant reply
- [ ] Average rating recalculated on each new rating; shown on search cards
- [ ] Publishes `review.submitted` event

## Dependencies

- BE-055, BE-070

## Definition of Done

- [ ] Integration test: rate → average updated → review listed
