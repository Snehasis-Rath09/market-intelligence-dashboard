-- Global Superstore reference data used by the market-intelligence dashboard.

CREATE TABLE superstore_records (

    id BIGSERIAL PRIMARY KEY,

    order_date DATE NOT NULL,

    category VARCHAR(100) NOT NULL,

    sub_category VARCHAR(100),

    region VARCHAR(50) NOT NULL,

    customer_segment VARCHAR(50) NOT NULL,

    sales NUMERIC(12,2) NOT NULL,

    profit NUMERIC(12,2) NOT NULL,

    quantity INTEGER,

    order_id VARCHAR(100) NOT NULL,

    ship_date DATE NOT NULL,

    ship_mode VARCHAR(100) NOT NULL,

    customer_id VARCHAR(100) NOT NULL,

    customer_name VARCHAR(255) NOT NULL,

    city VARCHAR(150) NOT NULL,

    state VARCHAR(150) NOT NULL,

    country VARCHAR(150) NOT NULL,

    postal_code VARCHAR(50),

    market VARCHAR(100) NOT NULL,

    product_id VARCHAR(100) NOT NULL,

    product_name VARCHAR(255) NOT NULL,

    discount NUMERIC(10,2) NOT NULL,

    shipping_cost NUMERIC(10,2) NOT NULL,

    order_priority VARCHAR(50) NOT NULL

);

CREATE INDEX idx_superstore_category
ON superstore_records(category);

CREATE INDEX idx_superstore_region
ON superstore_records(region);

CREATE INDEX idx_superstore_date
ON superstore_records(order_date);

CREATE INDEX idx_superstore_state
ON superstore_records(state);

CREATE INDEX idx_superstore_market
ON superstore_records(market);

CREATE INDEX idx_superstore_product
ON superstore_records(product_id);

CREATE INDEX idx_superstore_customer
ON superstore_records(customer_id);