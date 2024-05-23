alter table event_groups 
add column active bool default false;

alter table events 
drop column shift_start_time;

alter table events 
drop column notes;

drop table event_position_assignments ;

CREATE TABLE event_position_assignments (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT REFERENCES events(id),
    position_id BIGINT REFERENCES positions(id),
    user_id BIGINT REFERENCES users(id)
);