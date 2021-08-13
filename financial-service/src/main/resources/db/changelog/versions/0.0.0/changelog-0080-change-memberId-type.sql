--liquibase formatted sql
--changeset cor:financial-8
ALTER TABLE subscription
MODIFY member_id VARCHAR(64) NOT NULL;
UPDATE subscription SET member_id = '2bb88d2b-4313-43fb-9c2a-4f571a3fbba4' WHERE member_id = '1';
UPDATE subscription SET member_id = '1d8c8006-3778-4014-b086-6c26a055c643' WHERE member_id = '2';
--
ALTER TABLE flight
MODIFY member_id VARCHAR(64) NOT NULL;
UPDATE flight SET member_id = '2bb88d2b-4313-43fb-9c2a-4f571a3fbba4' WHERE member_id = '1';
UPDATE flight SET member_id = '1d8c8006-3778-4014-b086-6c26a055c643' WHERE member_id = '2';