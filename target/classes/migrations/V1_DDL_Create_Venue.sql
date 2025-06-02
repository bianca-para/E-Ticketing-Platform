CREATE TABLE IF NOT EXISTS venues (
                                      id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    capacity INTEGER NOT NULL
    );