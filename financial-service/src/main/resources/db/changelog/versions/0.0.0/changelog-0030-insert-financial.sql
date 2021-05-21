--liquibase formatted sql
--changeset cor:financial-3
INSERT INTO membership (id, member_id, payment_date, amount, validity_date) VALUES
(1, 1, "2021-02-14", 150, "2020-12-31"),
(2, 1, "2021-05-20", 150, "2021-12-31"),
(3, 2, "2021-03-11", 150, "2021-12-31");
INSERT INTO flight (id, member_id, aircraft, line_item, flight_date, flight_hours, amount ) VALUES
(1, 1, "F-GCNS CESSNA C152", "Vol local Avignon", "2021-05-14", 1, 129),
(2, 1, "F-GCNS CESSNA C152", "A/R Avignon/Montpellier", "2021-05-17", 1.4, 180.6);
