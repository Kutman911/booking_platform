CREATE TABLE IF NOT EXISTS appointments (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    specialist_id UUID NOT NULL,
    date_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES users (id),
    FOREIGN KEY (specialist_id) REFERENCES specialists (id)
    );
