CREATE TABLE IF NOT EXISTS users (
                                     id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    user_type VARCHAR(20) NOT NULL CHECK (user_type IN ('ADMIN', 'MODERATOR', 'PARTICIPANT')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS moderators (
                                          user_id VARCHAR(50) PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    permissions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS participants (
                                            user_id VARCHAR(50) PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    preferences TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

--constrangere pt un singur admin
CREATE UNIQUE INDEX IF NOT EXISTS unique_admin
    ON users (user_type)
    WHERE user_type = 'ADMIN';

--admin default
INSERT INTO users (id, name, email, user_type)
SELECT 'admin-001', 'System Administrator', 'admin@eventplanner.com', 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_type = 'ADMIN');