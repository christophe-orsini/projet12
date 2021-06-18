--liquibase formatted sql
--changeset cor:reservation-3
ALTER TABLE booking
ADD closed tinyint(1) DEFAULT 0;