# BE-036: SSE Order Tracking Stream

| Field | Value |
|-------|-------|
| **ID** | BE-036 |
| **Module** | `order-service` |
| **Sprint** | 3–4 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | OM-08 |

## Summary

Server-Sent Events stream for real-time order status and driver location updates to customers.

## Acceptance Criteria

- [ ] `GET /api/v1/orders/{id}/track` opens SSE stream (text/event-stream)
- [ ] Customer sees status change within 3s of backend update
- [ ] Events include: `status`, `driverLocation`, `eta`
- [ ] Stream closes when order `DELIVERED` or `CANCELLED`
- [ ] Auth: only order owner or admin can subscribe
- [ ] Heartbeat every 15s to keep connection alive

## Dependencies

- BE-033, BE-053

## Definition of Done

- [ ] Integration test: status change → SSE client receives event
- [ ] Load test: 100 concurrent SSE connections per instance
