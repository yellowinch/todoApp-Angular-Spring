CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    name  VARCHAR(128),
    completed BOOLEAN         NOT NULL
    );
