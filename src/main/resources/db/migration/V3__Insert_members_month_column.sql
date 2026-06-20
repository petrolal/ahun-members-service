ALTER TABLE members ADD COLUMN month_birthday INTEGER
    GENERATED ALWAYS AS (EXTRACT(MONTH FROM members.birthday)) STORED;

CREATE INDEX idx_members_mes ON members(month_birthday);