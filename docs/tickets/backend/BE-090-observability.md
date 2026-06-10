# BE-090: Observability Stack

| Field | Value |
|-------|-------|
| **ID** | BE-090 |
| **Module** | all services |
| **Sprint** | 11–12 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | §4.5 Observability |

## Summary

Structured logging, Prometheus metrics, and distributed tracing across all backend services.

## Acceptance Criteria

- [ ] JSON structured logs with `traceId`, `spanId`, `service`, `userId`, `orderId`
- [ ] Micrometer metrics: `http_requests_total`, `http_request_duration_seconds`
- [ ] Business counters: `orders_placed_total`, `payments_failed_total`
- [ ] Trace ID propagated HTTP → Kafka → HTTP
- [ ] Zipkin/Jaeger integration (or OTLP exporter)
- [ ] Actuator `/actuator/health`, `/actuator/prometheus` on all services

## Dependencies

- BE-001, all runnable services

## Definition of Done

- [ ] Trace visible across order → payment call in Jaeger
- [ ] Grafana dashboard JSON in `infrastructure/monitoring/` (optional scaffold)
