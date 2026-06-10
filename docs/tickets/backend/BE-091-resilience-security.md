# BE-091: Resilience & Security Hardening

| Field | Value |
|-------|-------|
| **ID** | BE-091 |
| **Module** | all services + `api-gateway` |
| **Sprint** | 11–12 |
| **Priority** | P0 |
| **Status** | Todo |
| **PRD** | §4.3 Reliability, §4.4 Security |

## Summary

Production resilience: circuit breakers, timeouts, retries, DLQ alerts, graceful shutdown.

## Acceptance Criteria

- [ ] Resilience4j circuit breaker on payment gateway and inter-service HTTP
- [ ] Connect timeout 2s, read timeout 5s on all outbound calls
- [ ] Retries only on idempotent ops with exponential backoff
- [ ] DLQ alert after 3 consumer failures (PagerDuty/Opsgenie hook or log alert)
- [ ] Graceful shutdown: drain Kafka consumers and HTTP connections
- [ ] Input validation on all endpoints; parameterized SQL only
- [ ] Secrets from env/Vault only

## Dependencies

- BE-002, BE-060, BE-090

## Definition of Done

- [ ] Chaos test: payment service down → circuit opens → fast fail
- [ ] Security scan: no secrets in repo
