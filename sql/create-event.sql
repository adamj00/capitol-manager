CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    show_id INT NOT NULL,
    event_start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    shift_start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    notes TEXT,
    CONSTRAINT fk_show FOREIGN KEY (show_id) REFERENCES shows (id)
);

INSERT INTO events (show_id, event_start_time, shift_start_time, notes) VALUES
(1, '2024-04-24 19:00:00', '2024-04-24 18:00:00', 'Opening night gala'),
(2, '2024-04-25 20:00:00', '2024-04-25 19:00:00', 'Special guest performance'),
(1, '2024-04-26 19:00:00', '2024-04-26 18:00:00', 'Second night show'),
(3, '2024-04-27 21:00:00', '2024-04-27 20:00:00', 'Late night comedy special'),
(2, '2024-04-28 20:00:00', '2024-04-28 19:00:00', 'Sunday family show');