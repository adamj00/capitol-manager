CREATE TABLE positions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    position_type VARCHAR(255) NOT NULL
);

INSERT INTO public.positions
("name", position_type)
values
('Bilety parter', 'TICKETS'),
('Szatnia parter', 'CLOAKROOM'),
('Widownia parter', 'AUDITORIUM');
