# BE-017: Photo Upload Mechanism

| Field | Value |
|-------|-------|
| **ID** | BE-017 |
| **Module** | `user-service` / `common` (TBD) |
| **Sprint** | TBD |
| **Priority** | P2 |
| **Status** | Todo |
| **PRD** | UM-05 |

## Summary

Build a dedicated, scalable mechanism to handle photo uploads from users (e.g. for user avatars, restaurant banners, menu items).

## Scope

- Evaluate and decide on the upload architecture: either direct `multipart/form-data` uploads handled by a backend service and then stored in an S3-compatible blob store OR having the backend generate and return pre-signed URLs (e.g. AWS S3 presigned URLs) to the frontend.
- Implement the upload/generation endpoints.
- Validate image types, size limits, and basic security checks (e.g., MIME type spoofing).
- Establish the storage layer connection (AWS S3, MinIO, or similar bucket).

## Acceptance Criteria

- [ ] Tech design document detailing chosen architectural approach (multipart vs pre-signed url) is written and approved.
- [ ] Upload endpoint is reachable and secured (requires authentication).
- [ ] Successful upload yields a permanent public/CDN URL.
- [ ] Bad inputs (files too large, wrong formats, executable payloads) are safely rejected with `400 Bad Request`.

## Dependencies

- BE-014 (Profile Management needs the resulting URL)

## Definition of Done

- [ ] Upload works end-to-end via an integration test.
