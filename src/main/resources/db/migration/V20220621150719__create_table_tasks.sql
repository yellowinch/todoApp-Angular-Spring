CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(128),
    completed   BOOLEAN         NOT NULL  DEFAULT FALSE,
    created_at timestamp default current_timestamp,
    modified_at timestamp default current_timestamp
    );
