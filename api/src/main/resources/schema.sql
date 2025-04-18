CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

ALTER TABLE users ADD COLUMN IF NOT EXISTS password VARCHAR(255);

CREATE TABLE IF NOT EXISTS chats (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    session_id UUID NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    messages JSONB,
    UNIQUE (user_id, session_id)
);

CREATE TABLE IF NOT EXISTS diagnosis_records (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    session_id UUID NOT NULL,
    primary_symptom VARCHAR(255),
    duration VARCHAR(255),
    severity VARCHAR(50),
    possible_condition VARCHAR(255),
    confidence VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);