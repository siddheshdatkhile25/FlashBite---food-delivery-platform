# BE-083: Admin User & Partner Management

| Field | Value |
|-------|-------|
| **ID** | BE-083 |
| **Module** | `user-service` + `analytics-service` |
| **Sprint** | 13–14 |
| **Priority** | P1 |
| **Status** | Todo |
| **PRD** | AP-02, AP-03, AP-04 |

## Summary

Admin workflows: search users, approve/suspend restaurants and drivers.

## Acceptance Criteria

- [ ] `GET /api/v1/admin/users?search=` by name, email, phone, ID
- [ ] `PATCH /api/v1/admin/users/{id}/suspend` disables login
- [ ] `PATCH /api/v1/admin/restaurants/{id}/approve` sets status `APPROVED`
- [ ] `PATCH /api/v1/admin/restaurants/{id}/suspend` hides from search
- [ ] Driver verification workflow: document upload → admin review → approve
- [ ] All admin actions audit-logged

## Dependencies

- BE-020, BE-013, BE-082

## Definition of Done

- [ ] Integration test: approve restaurant → visible in search
- [ ] Audit log entry verified for suspend action
