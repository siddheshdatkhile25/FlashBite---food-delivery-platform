CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS delivery_assignments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL,
    driver_id UUID,
    status VARCHAR(32) NOT NULL DEFAULT 'UNASSIGNED',
    accepted_at TIMESTAMPTZ,
    picked_up_at TIMESTAMPTZ,
    delivered_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_delivery_assignments_order UNIQUE (order_id)
);

CREATE INDEX IF NOT EXISTS idx_delivery_assignments_driver ON delivery_assignments(driver_id);
CREATE INDEX IF NOT EXISTS idx_delivery_assignments_status ON delivery_assignments(status);
