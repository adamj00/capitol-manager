ALTER TABLE users
ADD COLUMN password VARCHAR(255) NOT NULL DEFAULT 'capitol',
ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'EMPLOYEE';