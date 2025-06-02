CREATE TABLE IF NOT EXISTS event_attendees (
                                               id SERIAL PRIMARY KEY,
                                               event_id VARCHAR(50) NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    attendance_confirmed BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_attendee_event
    FOREIGN KEY (event_id) REFERENCES events(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_attendee_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT unique_event_user UNIQUE(event_id, user_id)
    );

CREATE INDEX IF NOT EXISTS idx_attendees_event ON event_attendees(event_id);
CREATE INDEX IF NOT EXISTS idx_attendees_user ON event_attendees(user_id);