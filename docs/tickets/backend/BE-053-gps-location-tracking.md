# BE-053: GPS Location Tracking

| Field | Value |
|-------|-------|
| **ID** | BE-053 |
| **Module** | `delivery-service` |
| **Sprint** | 7–8 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | DL-03 |

## Summary

High-frequency driver GPS updates stored in MongoDB; streamed to customers via SSE/Kafka.

## Acceptance Criteria

- [ ] `POST /api/v1/driver/location` accepts `{ lat, lng, timestamp }` every ~5s
- [ ] MongoDB document per active delivery with `location_trail` array and `current_location` GeoJSON Point
- [ ] TTL index: auto-delete trails after 30 days
- [ ] Publishes `driver.location.updated` to Kafka
- [ ] Customer map lag < 5s end-to-end

## MongoDB Schema

```javascript
{
  driver_id, order_id,
  location_trail: [{ lat, lng, ts }],
  current_location: { type: "Point", coordinates: [lng, lat] },
  status: "EN_ROUTE"
}
```

## Dependencies

- BE-052, BE-060, MongoDB in docker-compose

## Definition of Done

- [ ] Integration test: location POST → trail appended
- [ ] Geo index query returns nearby drivers
