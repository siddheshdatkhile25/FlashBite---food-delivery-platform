CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL,
    customer_id UUID NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    provider VARCHAR(64) NOT NULL,
    provider_payment_id VARCHAR(255),
    failure_reason TEXT,
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS refunds (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    payment_id UUID NOT NULL REFERENCES payments(id) ON DELETE CASCADE,
    order_id UUID NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    reason TEXT NOT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    provider_refund_id VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_payments_order ON payments(order_id);
CREATE INDEX IF NOT EXISTS idx_payments_status ON payments(status);
CREATE INDEX IF NOT EXISTS idx_refunds_payment ON refunds(payment_id);
CREATE INDEX IF NOT EXISTS idx_refunds_order ON refunds(order_id);
