--liquibase formatted sql
--changeset cor:financial-7
ALTER TABLE subscription
MODIFY payment_date date NOT NULL, 
MODIFY validity_date date NOT NULL;
ALTER TABLE flight
MODIFY flight_date date NOT NULL, 
MODIFY payment_date date DEFAULT NULL;