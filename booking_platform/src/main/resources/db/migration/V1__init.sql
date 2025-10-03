--baseline
CREATE TABLE IF NOT EXISTS flyway_init (
                                           id SERIAL PRIMARY KEY,
                                           created_at TIMESTAMP DEFAULT now()
    );
