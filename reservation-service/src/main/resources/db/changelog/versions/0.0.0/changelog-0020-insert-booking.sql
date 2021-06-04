--liquibase formatted sql
--changeset cor:reservation-2
INSERT INTO booking (id, member_id, aircraft_id, description, departure_time, arrival_time) VALUES
(1, 1, 1, "Tours de pistes", "2021-05-24 14:00:00", "2021-05-24 15:00:00");
