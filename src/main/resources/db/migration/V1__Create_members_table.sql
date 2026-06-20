CREATE TABLE members
(
    uuid        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at  TIMESTAMP,
    email       VARCHAR(255),
    member_name VARCHAR(255) NOT NULL,
    birthday    DATE
);