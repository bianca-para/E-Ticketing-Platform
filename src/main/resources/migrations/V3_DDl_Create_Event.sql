CREATE TABLE IF NOT EXISTS events (
                                      id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL CHECK (category IN ('MUSIC', 'TECH', 'SPORTS', 'ART', 'BUSINESS')),
    venue_id VARCHAR(50) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    max_attendees INTEGER,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_event_venue
    FOREIGN KEY (venue_id) REFERENCES venues(id)
    ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE INDEX IF NOT EXISTS idx_events_category ON events(category);
CREATE INDEX IF NOT EXISTS idx_events_date ON events(event_date);
CREATE INDEX IF NOT EXISTS idx_events_venue ON events(venue_id);