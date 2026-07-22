CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL,
    restaurant_id UUID NOT NULL,
    driver_id UUID,
    status VARCHAR(20) NOT NULL DEFAULT 'PLACED',
    total_amount NUMERIC(10, 2) NOT NULL,
    delivery_address JSONB NOT NULL,
    placed_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    delivered_at TIMESTAMPTZ,
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    menu_item_id UUID NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_orders_customer ON orders(customer_id, placed_at DESC);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status)
    WHERE status NOT IN ('DELIVERED', 'CANCELLED');
CREATE INDEX IF NOT EXISTS idx_order_items_order ON order_items(order_id);
