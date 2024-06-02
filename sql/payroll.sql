drop table payrolls ;
CREATE TABLE payrolls (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    hours DOUBLE PRECISION NOT NULL,
    accepted BOOLEAN not null,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

alter table payrolls drop column accepted;