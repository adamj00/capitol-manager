CREATE TABLE IF NOT EXISTS shows (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration INTEGER NOT NULL,
    stage_id INTEGER,
    additional_information TEXT,
    FOREIGN KEY (stage_id) REFERENCES stages (id)
);

INSERT INTO public.shows
(title, duration, stage_id, additional_information)
VALUES('Mock. Czarna burleska', 150, 1, 'https://www.teatr-capitol.pl/spektakle/mock-czarna-burleska/'),
('Mistrz i Małgorzata', 200, 1, 'https://www.teatr-capitol.pl/spektakle/mistrz-i-malgorzata/'),
('Blaszany bębenek', 230, 1, 'https://www.teatr-capitol.pl/spektakle/blaszany-bebenek/');
