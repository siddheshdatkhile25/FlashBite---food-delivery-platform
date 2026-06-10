# BE-093: Load Testing Critical Paths

| Field | Value |
|-------|-------|
| **ID** | BE-093 |
| **Module** | all services |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | §4.1 Performance, §4.2 Scalability |

## Summary

k6 or Gatling load tests simulating 10× expected peak; validate NFR targets before MVP launch.

## Acceptance Criteria

- [ ] Scenarios: search (200ms P95), place order (500/min sustained), SSE connections (1000 concurrent)
- [ ] API P95 < 300ms under target load
- [ ] Order placement throughput 500 orders/min sustained
- [ ] No error rate > 1% under normal peak
- [ ] Results documented with bottlenecks and remediation notes
- [ ] Scripts in `scripts/load/` or `infrastructure/load/`

## Targets (from PRD)

| Metric | Target |
|--------|--------|
| API P95 | < 300ms |
| Search P95 | < 200ms |
| Concurrent users | 10,000 |
| Order throughput | 500/min |

## Dependencies

- BE-002, BE-090, MVP feature-complete

## Definition of Done

- [ ] Load test report committed to `docs/`
- [ ] Critical regressions filed as follow-up tickets if any
