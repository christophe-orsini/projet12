--liquibase formatted sql
--changeset cor:reservation-4
ALTER TABLE booking
MODIFY member_id VARCHAR(64) NOT NULL;
UPDATE booking SET member_id = '2bb88d2b-4313-43fb-9c2a-4f571a3fbba4' WHERE member_id = '1';
UPDATE booking SET member_id = '1d8c8006-3778-4014-b086-6c26a055c643' WHERE member_id = '2';