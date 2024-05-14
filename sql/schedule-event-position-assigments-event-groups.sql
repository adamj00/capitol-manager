-- Tworzenie tabeli dla grup wydarzeń (event_groups)
CREATE TABLE event_groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tworzenie tabeli dla grafików (schedules)
CREATE TABLE schedules (
    id SERIAL PRIMARY KEY,
    event_group_id INT UNIQUE NOT NULL,
    FOREIGN KEY (event_group_id) REFERENCES event_groups (event_group_id)
);

-- Tworzenie tabeli dla przypisań pozycji do wydarzeń (event_position_assignments)
CREATE TABLE event_position_assignments (
    id SERIAL PRIMARY KEY,
    schedule_id INT NOT NULL,
    event_id INT NOT NULL,
    position_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules (schedule_id),
    FOREIGN KEY (event_id) REFERENCES events (event_id),
    FOREIGN KEY (position_id) REFERENCES positions (position_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);