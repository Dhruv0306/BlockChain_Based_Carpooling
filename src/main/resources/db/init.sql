-- Enable PostGIS extension
CREATE EXTENSION IF NOT EXISTS postgis;

-- Create rides table if it doesn't exist
CREATE TABLE IF NOT EXISTS rides (
    id BIGSERIAL PRIMARY KEY,
    driver_id BIGINT NOT NULL,
    start_point GEOMETRY(Point, 4326),
    end_point GEOMETRY(Point, 4326),
    departure_time TIMESTAMP,
    available_seats INTEGER,
    price DECIMAL(10,2),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create spatial index on rides table
CREATE INDEX IF NOT EXISTS idx_rides_start_point ON rides USING GIST (start_point);
CREATE INDEX IF NOT EXISTS idx_rides_end_point ON rides USING GIST (end_point); 