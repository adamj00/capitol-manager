CREATE TABLE stages (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    number_of_seats INT,
    address VARCHAR(255)
);

CREATE TABLE stage_positions (
    id SERIAL  PRIMARY key,
    stage_id int NOT NULL,
    position_id int NOT NULL,
    quantity int NOT NULL,
    CONSTRAINT fk_stage
        FOREIGN KEY (stage_id)
        REFERENCES stages(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_position
        FOREIGN KEY (position_id)
        REFERENCES positions(id)
        ON DELETE CASCADE
);

INSERT INTO public.stages
("name", number_of_seats, address)
VALUES('Duża scena', 700, 'Teatr Muzyczny Capitol, ul. Piłsudskiego 67, 50-019 Wrocław');


INSERT INTO public.stage_positions
(stage_id, position_id, quantity)
VALUES(1, 1, 2),
(1, 2, 2),
(1, 3, 2);




