drop table payrolls ;
CREATE TABLE payrolls (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    hours DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

alter table payrolls drop column accepted;

DO $$
DECLARE
    user_id INT;
BEGIN
    FOR user_id IN SELECT * FROM (VALUES (1411), (4), (1455), (1499), (2), (1543), (1587), (1631), (5), (1675), (3), (1719), (350), (351), (352), (355), (1763), (1807), (1851), (1895), (1939), (1983), (2027), (2071)) AS user_ids(id)
    LOOP
        INSERT INTO payrolls (user_id, date, start_time, end_time, hours)
        SELECT
            user_id,
            gs::DATE,
            '18:00'::TIME,
            '22:00'::TIME,
            4.0
        FROM generate_series('2024-05-01'::DATE, '2024-05-31'::DATE, '1 day'::INTERVAL) gs
        WHERE EXTRACT(DOW FROM gs) IN (4, 5, 6, 0); -- 0=Sunday, 1=Monday, ..., 6=Saturday
    END LOOP;
END $$;